package com.sgra.refuerzos;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RefuerzoIntegrationTests {

    private static final String ASIGNATURA = "{\"id\":1,\"nombre\":\"Dato enviado por el receptor de prueba\"}";
    private static final String NO_ENCONTRADA = "{\"codigo\":\"ASIGNATURA_NO_ENCONTRADA\"}";
    private static final AtomicInteger peticiones = new AtomicInteger();
    private static final AtomicReference<String> ultimaRuta = new AtomicReference<>();
    private static final AtomicReference<Respuesta> respuesta = new AtomicReference<>();
    private static final ExecutorService tareas = Executors.newVirtualThreadPerTaskExecutor();
    private static final JsonMapper json = JsonMapper.builder().build();
    private static final HttpClient cliente = HttpClient.newHttpClient();
    private static final HttpServer academico = iniciarAcademico();

    @LocalServerPort
    int puerto;

    @DynamicPropertySource
    static void configurar(DynamicPropertyRegistry registro) {
        registro.add("sgra.academico.base-url", () -> "http://127.0.0.1:" + academico.getAddress().getPort());
    }

    @BeforeEach
    void preparar() {
        peticiones.set(0);
        ultimaRuta.set(null);
        respuesta.set(new Respuesta(200, "application/json", ASIGNATURA, 0, 0, false));
    }

    @AfterAll
    static void cerrar() {
        academico.stop(0);
        tareas.shutdownNow();
        cliente.close();
    }

    @Test
    void devuelveElDatoRecibidoDelOtroProceso() throws Exception {
        HttpResponse<String> resultado = consultar("1");
        assertEquals(200, resultado.statusCode());
        assertEquals(json.readTree(ASIGNATURA), json.readTree(resultado.body()));
        assertEquals("/api/asignaturas/1", ultimaRuta.get());
        assertEquals(1, peticiones.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "abc", "2147483648"})
    void rechazaIdentificadoresInvalidosSinLlamarAlReceptor(String id) throws Exception {
        comprobarCodigo(consultar(id), 400, "ID_INVALIDO");
        assertEquals(0, peticiones.get());
    }

    @Test
    void conservaEl404YElCodigoDelReceptor() throws Exception {
        respuesta.set(new Respuesta(404, "application/json; charset=utf-8", NO_ENCONTRADA, 0, 0, false));
        comprobarCodigo(consultar("2"), 404, "ASIGNATURA_NO_ENCONTRADA");
        assertEquals("/api/asignaturas/2", ultimaRuta.get());
        assertEquals(1, peticiones.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 401, 500, 503})
    void traduceEstadosInesperadosAFalloDeDependencia(int estado) throws Exception {
        respuesta.set(new Respuesta(estado, "application/json", "{}", 0, 0, false));
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @Test
    void noSigueRedirecciones() throws Exception {
        respuesta.set(new Respuesta(302, "application/json", "{}", 0, 0, false));
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"no es JSON", "null", "[]", "{\"id\":2,\"nombre\":\"Otra\"}",
            "{\"id\":\"1\",\"nombre\":\"Texto\"}", "{\"id\":1}", "{\"id\":1,\"nombre\":\" \"}",
            "{\"id\":4294967297,\"nombre\":\"Fuera de rango\"}"})
    void rechazaRespuestasIncompatibles(String cuerpo) throws Exception {
        respuesta.set(new Respuesta(200, "application/json", cuerpo, 0, 0, false));
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @Test
    void rechazaUnTipoDeContenidoIncorrecto() throws Exception {
        respuesta.set(new Respuesta(200, "text/html", ASIGNATURA, 0, 0, false));
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @Test
    void rechazaUn404SinElCodigoPactado() throws Exception {
        respuesta.set(new Respuesta(404, "application/json", "{\"codigo\":\"OTRO\"}", 0, 0, false));
        comprobarCodigo(consultar("2"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @Test
    void limitaLaEsperaDeCabecerasSinReintentar() throws Exception {
        respuesta.set(new Respuesta(200, "application/json", ASIGNATURA, 4000, 0, false));
        comprobarEsperaLimitada();
    }

    @Test
    void limitaLaEsperaDelCuerpoAunqueYaLlegaronLasCabeceras() throws Exception {
        respuesta.set(new Respuesta(200, "application/json", ASIGNATURA, 0, 4000, false));
        comprobarEsperaLimitada();
    }

    @Test
    void noReintentaCuandoElReceptorCierraLaConexion() throws Exception {
        respuesta.set(new Respuesta(200, "application/json", ASIGNATURA, 0, 0, true));
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        assertEquals(1, peticiones.get());
    }

    @Test
    void informaFalloCuandoNoHayReceptorEscuchando() throws Exception {
        int puertoCerrado;
        try (ServerSocket reserva = new ServerSocket(0, 0, java.net.InetAddress.getLoopbackAddress())) {
            puertoCerrado = reserva.getLocalPort();
        }
        AcademicoClient sinReceptor = new AcademicoClient(json, "http://127.0.0.1:" + puertoCerrado, 2000);
        try {
            var resultado = sinReceptor.consultar(1);
            assertEquals(503, resultado.getStatusCode().value());
            assertEquals("SERVICIO_ACADEMICO_NO_DISPONIBLE",
                    ((java.util.Map<?, ?>) resultado.getBody()).get("codigo"));
        } finally {
            sinReceptor.cerrar();
        }
    }

    private void comprobarEsperaLimitada() throws Exception {
        long inicio = System.nanoTime();
        comprobarCodigo(consultar("1"), 503, "SERVICIO_ACADEMICO_NO_DISPONIBLE");
        long duracion = Duration.ofNanos(System.nanoTime() - inicio).toMillis();
        assertTrue(duracion < 3500, "La llamada debería terminar antes de que el receptor responda: " + duracion);
        assertEquals(1, peticiones.get());
    }

    private HttpResponse<String> consultar(String id) throws Exception {
        return cliente.send(HttpRequest.newBuilder(
                URI.create("http://127.0.0.1:" + puerto + "/api/demo/asignaturas/" + id))
                .timeout(Duration.ofSeconds(6)).GET().build(), HttpResponse.BodyHandlers.ofString());
    }

    private static void comprobarCodigo(HttpResponse<String> resultado, int estado, String codigo) {
        assertEquals(estado, resultado.statusCode());
        JsonNode cuerpo = json.readTree(resultado.body());
        assertEquals(codigo, cuerpo.path("codigo").stringValue());
    }

    private static HttpServer iniciarAcademico() {
        try {
            HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            servidor.setExecutor(tareas);
            servidor.createContext("/", RefuerzoIntegrationTests::atender);
            servidor.start();
            return servidor;
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void atender(HttpExchange intercambio) {
        peticiones.incrementAndGet();
        ultimaRuta.set(intercambio.getRequestURI().getPath());
        Respuesta configurada = respuesta.get();
        try (intercambio) {
            if (configurada.cerrarConexion()) {
                return;
            }
            Thread.sleep(configurada.esperaCabeceras());
            intercambio.getResponseHeaders().set("Content-Type", configurada.tipo());
            if (configurada.estado() == 302) {
                intercambio.getResponseHeaders().set("Location", "/otra-ruta");
            }
            byte[] cuerpo = configurada.cuerpo().getBytes(StandardCharsets.UTF_8);
            intercambio.sendResponseHeaders(configurada.estado(), cuerpo.length);
            if (configurada.esperaCuerpo() > 0) {
                intercambio.getResponseBody().write(cuerpo, 0, 1);
                intercambio.getResponseBody().flush();
                Thread.sleep(configurada.esperaCuerpo());
                intercambio.getResponseBody().write(cuerpo, 1, cuerpo.length - 1);
            } else {
                intercambio.getResponseBody().write(cuerpo);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (IOException exception) {
            // A timed-out client closes its connection before the deliberately delayed body.
        }
    }

    private record Respuesta(int estado, String tipo, String cuerpo,
            long esperaCabeceras, long esperaCuerpo, boolean cerrarConexion) {
    }
}
