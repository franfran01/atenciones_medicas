package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.Atencion;
import com.duoc.atenciones.service.AtencionService;
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
public class AtencionController {

    private final AtencionService atencionService;

    public AtencionController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping("/atenciones")
    public CollectionModel<EntityModel<Atencion>> obtenerAtenciones(
            @RequestParam(required = false) Integer pacienteId) {
        List<EntityModel<Atencion>> atenciones = atencionService.obtenerAtenciones(pacienteId).stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                atenciones,
                linkTo(methodOn(AtencionController.class).obtenerAtenciones(pacienteId)).withSelfRel());
    }

    @GetMapping("/atenciones/{id}")
    public EntityModel<Atencion> obtenerAtencionPorId(@PathVariable Integer id) {
        return toModel(atencionService.obtenerAtencionPorId(id));
    }

    @PostMapping("/atenciones")
    public ResponseEntity<EntityModel<Atencion>> crearAtencion(@Valid @RequestBody Atencion atencion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(atencionService.crearAtencion(atencion)));
    }

    @PutMapping("/atenciones/{id}")
    public EntityModel<Atencion> actualizarAtencion(@PathVariable Integer id, @Valid @RequestBody Atencion atencion) {
        return toModel(atencionService.actualizarAtencion(id, atencion));
    }

    @DeleteMapping("/atenciones/{id}")
    public ResponseEntity<Void> eliminarAtencion(@PathVariable Integer id) {
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Atencion> toModel(Atencion atencion) {
        return EntityModel.of(
                atencion,
                linkTo(methodOn(AtencionController.class).obtenerAtencionPorId(atencion.getId())).withSelfRel(),
                linkTo(methodOn(AtencionController.class).obtenerAtenciones(null)).withRel("atenciones"));
    }
}
