package com.duoc.atenciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialMedico {

    private Paciente paciente;
    private List<Consulta> consultas;
    private List<Atencion> atenciones;
}
