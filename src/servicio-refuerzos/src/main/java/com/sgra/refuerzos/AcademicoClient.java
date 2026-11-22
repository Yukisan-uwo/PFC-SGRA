package com.sgra.refuerzos;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jakarta.annotation.PreDestroy;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@Component
public class AcademicoClient {

    private static final Logger log = LoggerFactory.getLogger(AcademicoClient.class);

    private final CloseableHttpAsyncClient http;
    private final JsonMapper json;
    private final URI base;
    private final Duration timeout;

    public AcademicoClient(JsonMapper json,
            @Value("${sgra.academico.base-url}") String baseUrl,
            @Value("${sgra.academico.timeout-ms}") long timeoutMillis) {
        if (timeoutMillis <= 0) {
            throw new IllegalArgumentException("El tiempo de espera debe ser positivo");
        }
        this.base = URI.create(baseUrl);
        if (base.getHost() == null || !("http".equals(base.getScheme()) || "https".equals(base.getScheme()))) {
            throw new IllegalArgumentException("La URL del servicio académico debe ser HTTP o HTTPS");
        }
        this.json = json;
        this.timeout = Duration.ofMillis(timeoutMillis);
        this.http = HttpAsyncClients.custom()
                .disableAutomaticRetries()
                .disableRedirectHandling()
                .disableCookieManagement()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setResponseTimeout(Timeout.ofMilliseconds(timeoutMillis)).build())
                .build();
        this.http.start();
    }

    public ResponseEntity<?> consultar(int id) {
        long inicio = System.nanoTime();
        int estado = 503;
        Future<SimpleHttpResponse> llamada = null;
        try {
            URI destino = base.resolve("/api/asignaturas/" + id);
            SimpleHttpRequest request = SimpleRequestBuilder.get(destino)
                    .setHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                    .build();
            log.info("Consulta saliente servicio=academico ruta={}", destino.getPath());
            llamada = http.execute(request, null);
            // Waiting for the complete body covers connection, headers and a slow response body.
            long restante = timeout.toNanos() - (System.nanoTime() - inicio);
            if (restante <= 0) {
                return noDisponible();
            }
            SimpleHttpResponse respuesta = llamada.get(restante, TimeUnit.NANOSECONDS);
            if (respuesta.getCode() != 200 && respuesta.getCode() != 404) {
                return noDisponible();
            }
            var cabeceraTipo = respuesta.getFirstHeader("Content-Type");
            String tipo = cabeceraTipo == null ? "" : cabeceraTipo.getValue();
            if (tipo.isBlank() || !MediaType.APPLICATION_JSON.isCompatibleWith(MediaType.parseMediaType(tipo))) {
                return noDisponible();
            }
            JsonNode cuerpo = json.readTree(respuesta.getBodyBytes());
            if (cuerpo == null || !cuerpo.isObject()) {
                return noDisponible();
            }
            if (respuesta.getCode() == 200) {
                JsonNode asignaturaId = cuerpo.path("id");
                JsonNode nombre = cuerpo.path("nombre");
                if (!asignaturaId.isIntegralNumber() || !asignaturaId.canConvertToInt()
                        || asignaturaId.intValue() != id || !nombre.isTextual()
                        || nombre.stringValue().isBlank()) {
                    return noDisponible();
                }
            } else if (!"ASIGNATURA_NO_ENCONTRADA".equals(cuerpo.path("codigo").stringValue())) {
                return noDisponible();
            }
            estado = respuesta.getCode();
            return ResponseEntity.status(estado).contentType(MediaType.APPLICATION_JSON).body(cuerpo);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return noDisponible();
        } catch (ExecutionException | TimeoutException | RuntimeException exception) {
            log.warn("Consulta académica fallida motivo={}", exception.getClass().getSimpleName());
            return noDisponible();
        } finally {
            if (llamada != null && !llamada.isDone()) {
                llamada.cancel(true);
            }
            long duracionMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - inicio);
            log.info("Consulta terminada servicio=academico id={} estado={} duracionMs={}",
                    id, estado, duracionMillis);
        }
    }

    private ResponseEntity<?> noDisponible() {
        return ResponseEntity.status(503)
                .body(Map.of("codigo", "SERVICIO_ACADEMICO_NO_DISPONIBLE"));
    }

    @PreDestroy
    void cerrar() {
        http.close(CloseMode.IMMEDIATE);
    }
}
