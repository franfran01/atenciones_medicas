package com.duoc.atenciones.repository;

import com.duoc.atenciones.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    boolean existsByRutIgnoreCase(String rut);

    boolean existsByRutIgnoreCaseAndIdNot(String rut, Integer id);

    @Query("SELECT p FROM Paciente p WHERE LOWER(REPLACE(p.rut, '.', '')) = LOWER(REPLACE(:rut, '.', ''))")
    Optional<Paciente> findByRutNormalizado(@Param("rut") String rut);
}
