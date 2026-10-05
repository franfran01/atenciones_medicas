package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.HistorialMedico;
import com.duoc.atenciones.model.Paciente;
import com.duoc.atenciones.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/pacientes")
    public CollectionModel<EntityModel<Paciente>> obtenerPacientes() {
        List<EntityModel<Paciente>> pacientes = pacienteService.obtenerPacientes().stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                pacientes,
                linkTo(methodOn(PacienteController.class).obtenerPacientes()).withSelfRel());
    }

    @GetMapping("/pacientes/{id}")
    public EntityModel<Paciente> obtenerPacientePorId(@PathVariable Integer id) {
        return toModel(pacienteService.obtenerPacientePorId(id));
    }

    @GetMapping("/pacientes/{id}/historial")
    public EntityModel<HistorialMedico> obtenerHistorial(@PathVariable Integer id) {
        HistorialMedico historial = pacienteService.obtenerHistorial(id);
        return EntityModel.of(
                historial,
                linkTo(methodOn(PacienteController.class).obtenerHistorial(id)).withSelfRel(),
                linkTo(methodOn(PacienteController.class).obtenerPacientePorId(id)).withRel("paciente"),
                linkTo(methodOn(ConsultaController.class).obtenerConsultas(id)).withRel("consultas"),
                linkTo(methodOn(AtencionController.class).obtenerAtenciones(id)).withRel("atenciones"));
    }

    @GetMapping("/pacientes/rut/{rut}")
    public EntityModel<Paciente> obtenerPacientePorRut(@PathVariable String rut) {
        return toModel(pacienteService.obtenerPacientePorRut(rut));
    }

    @PostMapping("/pacientes")
    public ResponseEntity<EntityModel<Paciente>> crearPaciente(@Valid @RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(pacienteService.crearPaciente(paciente)));
    }

    @PutMapping("/pacientes/{id}")
    public EntityModel<Paciente> actualizarPaciente(@PathVariable Integer id, @Valid @RequestBody Paciente paciente) {
        return toModel(pacienteService.actualizarPaciente(id, paciente));
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Paciente> toModel(Paciente paciente) {
        return EntityModel.of(
                paciente,
                linkTo(methodOn(PacienteController.class).obtenerPacientePorId(paciente.getId())).withSelfRel(),
                linkTo(methodOn(PacienteController.class).obtenerPacientes()).withRel("pacientes"));
    }
}
