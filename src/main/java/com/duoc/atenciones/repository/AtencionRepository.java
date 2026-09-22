package com.duoc.atenciones.repository;

import com.duoc.atenciones.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    List<Atencion> findByPacienteId(Integer pacienteId);

    boolean existsByPacienteId(Integer pacienteId);

    boolean existsByConsultaId(Integer consultaId);
}
