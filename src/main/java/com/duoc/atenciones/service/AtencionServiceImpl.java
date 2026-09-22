package com.duoc.atenciones.service;

import com.duoc.atenciones.exception.ConflictoException;
import com.duoc.atenciones.exception.RecursoNoEncontradoException;
import com.duoc.atenciones.exception.SolicitudInvalidaException;
import com.duoc.atenciones.model.Atencion;
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
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public AtencionServiceImpl(
            AtencionRepository atencionRepository,
            PacienteRepository pacienteRepository,
            ConsultaRepository consultaRepository) {
        this.atencionRepository = atencionRepository;
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    public List<Atencion> obtenerAtenciones(Integer pacienteId) {
        if (pacienteId == null) {
            return atencionRepository.findAll();
        }
        if (pacienteId < 1) {
            throw new SolicitudInvalidaException("El pacienteId debe ser un numero positivo");
        }
        validarPaciente(pacienteId);
        return atencionRepository.findByPacienteId(pacienteId);
    }

    @Override
    public Atencion obtenerAtencionPorId(Integer id) {
        return atencionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una atencion con id " + id));
    }

    @Override
    @Transactional
    public Atencion crearAtencion(Atencion atencion) {
        if (atencion.getId() == null) {
            atencion.setId(siguienteId());
        } else if (atencionRepository.existsById(atencion.getId())) {
            throw new ConflictoException("Ya existe una atencion con id " + atencion.getId());
        }
        validarPacienteYConsulta(atencion);

        Atencion creada = atencionRepository.save(atencion);
        log.info("Atencion creada con id {}", creada.getId());
        return creada;
    }

    @Override
    @Transactional
    public Atencion actualizarAtencion(Integer id, Atencion atencion) {
        obtenerAtencionPorId(id);
        validarPacienteYConsulta(atencion);
        atencion.setId(id);
        Atencion actualizada = atencionRepository.save(atencion);
        log.info("Atencion actualizada con id {}", id);
        return actualizada;
    }

    @Override
    @Transactional
    public void eliminarAtencion(Integer id) {
        obtenerAtencionPorId(id);
        atencionRepository.deleteById(id);
        log.info("Atencion eliminada con id {}", id);
    }

    private void validarPaciente(Integer pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new RecursoNoEncontradoException("No existe un paciente con id " + pacienteId);
        }
    }

    private void validarPacienteYConsulta(Atencion atencion) {
        validarPaciente(atencion.getPacienteId());
        Consulta consulta = consultaRepository.findById(atencion.getConsultaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una consulta con id " + atencion.getConsultaId()));
        if (!consulta.getPacienteId().equals(atencion.getPacienteId())) {
            throw new SolicitudInvalidaException(
                    "La consulta " + atencion.getConsultaId() + " no pertenece al paciente " + atencion.getPacienteId());
        }
    }

    private Integer siguienteId() {
        return atencionRepository.findAll().stream()
                .map(Atencion::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }
}
