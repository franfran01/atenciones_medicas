package com.duoc.atenciones.service;

import com.duoc.atenciones.model.Consulta;

import java.util.List;

public interface ConsultaService {

    List<Consulta> obtenerConsultas(Integer pacienteId);

    Consulta obtenerConsultaPorId(Integer id);

    Consulta crearConsulta(Consulta consulta);

    Consulta actualizarConsulta(Integer id, Consulta consulta);

    void eliminarConsulta(Integer id);
}
