package com.duoc.atenciones.repository;

import com.duoc.atenciones.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    List<Consulta> findByPacienteId(Integer pacienteId);

    boolean existsByPacienteId(Integer pacienteId);
}
