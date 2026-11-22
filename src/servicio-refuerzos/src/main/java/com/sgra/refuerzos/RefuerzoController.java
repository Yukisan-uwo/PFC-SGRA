package com.sgra.refuerzos;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo/asignaturas")
public class RefuerzoController {

    private final AcademicoClient academico;

    public RefuerzoController(AcademicoClient academico) {
        this.academico = academico;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarAsignatura(@PathVariable String id) {
        int numero;
        try {
            numero = Integer.parseInt(id);
        } catch (NumberFormatException exception) {
            return idInvalido();
        }
        if (numero <= 0) {
            return idInvalido();
        }
        return academico.consultar(numero);
    }

    private ResponseEntity<?> idInvalido() {
        return ResponseEntity.badRequest().body(Map.of("codigo", "ID_INVALIDO"));
    }
}
