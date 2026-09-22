package com.duoc.atenciones.service;

import com.duoc.atenciones.exception.ConflictoException;
import com.duoc.atenciones.exception.RecursoNoEncontradoException;
import com.duoc.atenciones.exception.SolicitudInvalidaException;
import com.duoc.atenciones.model.HistorialMedico;
import com.duoc.atenciones.model.Paciente;
import com.duoc.atenciones.repository.AtencionRepository;
import com.duoc.atenciones.repository.ConsultaRepository;
import com.duoc.atenciones.repository.PacienteRepository;
import com.duoc.atenciones.util.RutUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final AtencionRepository atencionRepository;

    public PacienteServiceImpl(
            PacienteRepository pacienteRepository,
            ConsultaRepository consultaRepository,
            AtencionRepository atencionRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.atencionRepository = atencionRepository;
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente obtenerPacientePorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un paciente con id " + id));
    }

    @Override
    public Paciente obtenerPacientePorRut(String rut) {
        if (!RutUtil.tieneFormatoValido(rut)) {
            throw new SolicitudInvalidaException("El formato del RUT no es valido");
        }
        return pacienteRepository.findByRutNormalizado(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un paciente con RUT " + rut));
    }

    @Override
    public HistorialMedico obtenerHistorial(Integer id) {
        Paciente paciente = obtenerPacientePorId(id);
        return new HistorialMedico(
                paciente,
                consultaRepository.findByPacienteId(id),
                atencionRepository.findByPacienteId(id)
        );
    }

    @Override
    @Transactional
    public Paciente crearPaciente(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(siguienteId());
        } else if (pacienteRepository.existsById(paciente.getId())) {
            throw new ConflictoException("Ya existe un paciente con id " + paciente.getId());
        }
        if (pacienteRepository.existsByRutIgnoreCase(paciente.getRut())) {
            throw new ConflictoException("Ya existe un paciente con el RUT " + paciente.getRut());
        }

        Paciente creado = pacienteRepository.save(paciente);
        log.info("Paciente creado con id {}", creado.getId());
        return creado;
    }

    @Override
    @Transactional
    public Paciente actualizarPaciente(Integer id, Paciente paciente) {
        obtenerPacientePorId(id);
        if (pacienteRepository.existsByRutIgnoreCaseAndIdNot(paciente.getRut(), id)) {
            throw new ConflictoException("Ya existe un paciente con el RUT " + paciente.getRut());
        }
        paciente.setId(id);
        Paciente actualizado = pacienteRepository.save(paciente);
        log.info("Paciente actualizado con id {}", id);
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminarPaciente(Integer id) {
        obtenerPacientePorId(id);
        if (consultaRepository.existsByPacienteId(id) || atencionRepository.existsByPacienteId(id)) {
            throw new ConflictoException(
                    "No se puede eliminar el paciente " + id + " porque tiene consultas o atenciones asociadas");
        }
        pacienteRepository.deleteById(id);
        log.info("Paciente eliminado con id {}", id);
    }

    private Integer siguienteId() {
        return pacienteRepository.findAll().stream()
                .map(Paciente::getId)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }
}
