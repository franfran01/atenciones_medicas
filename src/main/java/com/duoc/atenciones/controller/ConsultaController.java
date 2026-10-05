package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.Consulta;
import com.duoc.atenciones.service.ConsultaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/consultas")
    public CollectionModel<EntityModel<Consulta>> obtenerConsultas(
            @RequestParam(required = false) Integer pacienteId) {
        List<EntityModel<Consulta>> consultas = consultaService.obtenerConsultas(pacienteId).stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                consultas,
                linkTo(methodOn(ConsultaController.class).obtenerConsultas(pacienteId)).withSelfRel());
    }

    @GetMapping("/consultas/{id}")
    public EntityModel<Consulta> obtenerConsultaPorId(@PathVariable Integer id) {
        return toModel(consultaService.obtenerConsultaPorId(id));
    }

    @PostMapping("/consultas")
    public ResponseEntity<EntityModel<Consulta>> crearConsulta(@Valid @RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(consultaService.crearConsulta(consulta)));
    }

    @PutMapping("/consultas/{id}")
    public EntityModel<Consulta> actualizarConsulta(@PathVariable Integer id, @Valid @RequestBody Consulta consulta) {
        return toModel(consultaService.actualizarConsulta(id, consulta));
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Integer id) {
        consultaService.eliminarConsulta(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Consulta> toModel(Consulta consulta) {
        return EntityModel.of(
                consulta,
                linkTo(methodOn(ConsultaController.class).obtenerConsultaPorId(consulta.getId())).withSelfRel(),
                linkTo(methodOn(ConsultaController.class).obtenerConsultas(null)).withRel("consultas"));
    }
}
