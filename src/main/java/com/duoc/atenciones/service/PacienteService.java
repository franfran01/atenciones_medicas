package com.duoc.atenciones.service;

import com.duoc.atenciones.model.HistorialMedico;
import com.duoc.atenciones.model.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> obtenerPacientes();

    Paciente obtenerPacientePorId(Integer id);

    Paciente obtenerPacientePorRut(String rut);

    HistorialMedico obtenerHistorial(Integer id);

    Paciente crearPaciente(Paciente paciente);

    Paciente actualizarPaciente(Integer id, Paciente paciente);

    void eliminarPaciente(Integer id);
}
