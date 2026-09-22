package com.duoc.atenciones.service;

import com.duoc.atenciones.exception.ConflictoException;
import com.duoc.atenciones.exception.RecursoNoEncontradoException;
import com.duoc.atenciones.exception.SolicitudInvalidaException;
import com.duoc.atenciones.model.Consulta;
import com.duoc.atenciones.repository.AtencionRepository;
import com.duoc.atenciones.repository.ConsultaRepository;
import com.duoc.atenciones.repository.PacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final AtencionRepository atencionRepository;

    public ConsultaServiceImpl(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            AtencionRepository atencionRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.atencionRepository = atencionRepository;
    }

    @Override
    public List<Consulta> obtenerConsultas(Integer pacienteId) {
        if (pacienteId == null) {
            return consultaRepository.findAll();
        }
        if (pacienteId < 1) {
            throw new SolicitudInvalidaException("El pacienteId debe ser un numero positivo");
        }
        validarPaciente(pacienteId);
        return consultaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public Consulta obtenerConsultaPorId(Integer id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una consulta con id " + id));
    }

    @Override
    @Transactional
    public Consulta crearConsulta(Consulta consulta) {
        if (consulta.getId() == null) {
            consulta.setId(siguienteId());
        } else if (consultaRepository.existsById(consulta.getId())) {
            throw new ConflictoException("Ya existe una consulta con id " + consulta.getId());
        }
        validarPaciente(consulta.getPacienteId());

        Consulta creada = consultaRepository.save(consulta);
        log.info("Consulta creada con id {}", creada.getId());
        return creada;
    }

    @Override
    @Transactional
    public Consulta actualizarConsulta(Integer id, Consulta consulta) {
        obtenerConsultaPorId(id);
        validarPaciente(consulta.getPacienteId());
        consulta.setId(id);
        Consulta actualizada = consultaRepository.save(consulta);
        log.info("Consulta actualizada con id {}", id);
        return actualizada;
    }

    @Override
    @Transactional
    public void eliminarConsulta(Integer id) {
        obtenerConsultaPorId(id);
        if (atencionRepository.existsByConsultaId(id)) {
            throw new ConflictoException("No se puede eliminar la consulta " + id + " porque tiene atenciones asociadas");
        }
        consultaRepository.deleteById(id);
        log.info("Consulta eliminada con id {}", id);
    }

    private void validarPaciente(Integer pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new RecursoNoEncontradoException("No existe un paciente con id " + pacienteId);
        }
    }

    private Integer siguienteId() {
        return consultaRepository.findAll().stream()
                .map(Consulta::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }
}
