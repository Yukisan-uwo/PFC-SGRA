package com.sgra.refuerzos;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api/demo/asignaturas")
public class ConsultaAsignaturaController {

    private final RestClient clienteAcademico;

    public ConsultaAsignaturaController() {
        SimpleClientHttpRequestFactory fabrica =
                new SimpleClientHttpRequestFactory();

        fabrica.setConnectTimeout(Duration.ofSeconds(1));
        fabrica.setReadTimeout(Duration.ofSeconds(1));

        this.clienteAcademico = RestClient.builder()
                .baseUrl("http://127.0.0.1:8082")
                .requestFactory(fabrica)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarAsignatura(
            @PathVariable String id) {

        int idNumerico;

        try {
            idNumerico = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("codigo", "ID_INVALIDO"));
        }

        if (idNumerico <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("codigo", "ID_INVALIDO"));
        }

        try {
            ResponseEntity<Map> respuesta =
                    clienteAcademico.get()
                            .uri("/api/asignaturas/{id}", idNumerico)
                            .retrieve()
                            .toEntity(Map.class);

            Map<?, ?> datos = respuesta.getBody();

            if (datos == null
                    || !(datos.get("id") instanceof Number)
                    || ((Number) datos.get("id")).intValue()
                            != idNumerico
                    || !"Asignatura de demostración"
                            .equals(datos.get("nombre"))) {
                return servicioNoDisponible();
            }

            return ResponseEntity.ok(datos);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "codigo",
                            "ASIGNATURA_NO_ENCONTRADA"));

        } catch (RestClientException e) {
            return servicioNoDisponible();
        }
    }

    private ResponseEntity<Map<String, String>>
            servicioNoDisponible() {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "codigo",
                        "SERVICIO_ACADEMICO_NO_DISPONIBLE"));
    }
}
