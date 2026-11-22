package com.sgra.academico;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

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

        if (idNumerico != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "codigo", "ASIGNATURA_NO_ENCONTRADA"));
        }

        return ResponseEntity.ok(Map.of(
                "id", 1,
                "nombre", "Asignatura de demostración"));
    }
}
