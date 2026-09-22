package com.duoc.atenciones.service;

import com.duoc.atenciones.model.Atencion;

import java.util.List;

public interface AtencionService {

    List<Atencion> obtenerAtenciones(Integer pacienteId);

    Atencion obtenerAtencionPorId(Integer id);

    Atencion crearAtencion(Atencion atencion);

    Atencion actualizarAtencion(Integer id, Atencion atencion);

    void eliminarAtencion(Integer id);
}
