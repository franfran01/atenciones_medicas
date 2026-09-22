package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.HistorialMedico;
import com.duoc.atenciones.model.Paciente;
import com.duoc.atenciones.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/pacientes")
    public List<Paciente> obtenerPacientes() {
        return pacienteService.obtenerPacientes();
    }

    @GetMapping("/pacientes/{id}")
    public Paciente obtenerPacientePorId(@PathVariable Integer id) {
        return pacienteService.obtenerPacientePorId(id);
    }

    @GetMapping("/pacientes/{id}/historial")
    public HistorialMedico obtenerHistorial(@PathVariable Integer id) {
        return pacienteService.obtenerHistorial(id);
    }

    @GetMapping("/pacientes/rut/{rut}")
    public Paciente obtenerPacientePorRut(@PathVariable String rut) {
        return pacienteService.obtenerPacientePorRut(rut);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.crearPaciente(paciente));
    }

    @PutMapping("/pacientes/{id}")
    public Paciente actualizarPaciente(@PathVariable Integer id, @Valid @RequestBody Paciente paciente) {
        return pacienteService.actualizarPaciente(id, paciente);
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
