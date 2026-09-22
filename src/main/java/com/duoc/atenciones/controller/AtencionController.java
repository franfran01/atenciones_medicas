package com.duoc.atenciones.controller;

import com.duoc.atenciones.model.Atencion;
import com.duoc.atenciones.service.AtencionService;
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
public class AtencionController {

    private final AtencionService atencionService;

    public AtencionController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping("/atenciones")
    public List<Atencion> obtenerAtenciones(@RequestParam(required = false) Integer pacienteId) {
        return atencionService.obtenerAtenciones(pacienteId);
    }

    @GetMapping("/atenciones/{id}")
    public Atencion obtenerAtencionPorId(@PathVariable Integer id) {
        return atencionService.obtenerAtencionPorId(id);
    }

    @PostMapping("/atenciones")
    public ResponseEntity<Atencion> crearAtencion(@Valid @RequestBody Atencion atencion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atencionService.crearAtencion(atencion));
    }

    @PutMapping("/atenciones/{id}")
    public Atencion actualizarAtencion(@PathVariable Integer id, @Valid @RequestBody Atencion atencion) {
        return atencionService.actualizarAtencion(id, atencion);
    }

    @DeleteMapping("/atenciones/{id}")
    public ResponseEntity<Void> eliminarAtencion(@PathVariable Integer id) {
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }
}
