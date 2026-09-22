package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.Consulta;
import com.duoc.atenciones.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/consultas")
    public List<Consulta> obtenerConsultas(@RequestParam(required = false) Integer pacienteId) {
        return consultaService.obtenerConsultas(pacienteId);
    }

    @GetMapping("/consultas/{id}")
    public Consulta obtenerConsultaPorId(@PathVariable Integer id) {
        return consultaService.obtenerConsultaPorId(id);
    }

    @PostMapping("/consultas")
    public ResponseEntity<Consulta> crearConsulta(@Valid @RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.crearConsulta(consulta));
    }

    @PutMapping("/consultas/{id}")
    public Consulta actualizarConsulta(@PathVariable Integer id, @Valid @RequestBody Consulta consulta) {
        return consultaService.actualizarConsulta(id, consulta);
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Integer id) {
        consultaService.eliminarConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
