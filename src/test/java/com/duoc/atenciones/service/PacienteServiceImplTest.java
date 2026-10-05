package com.duoc.atenciones.service;

import com.duoc.atenciones.exception.ConflictoException;
import com.duoc.atenciones.model.Paciente;
import com.duoc.atenciones.repository.AtencionRepository;
import com.duoc.atenciones.repository.ConsultaRepository;
import com.duoc.atenciones.repository.PacienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente paciente;

    @BeforeEach
    void prepararPaciente() {
        paciente = new Paciente(
                100,
                "19.999.999-9",
                "Valentina",
                "Morales",
                "1992-08-15",
                "+56919999999",
                "valentina.morales@correo.cl");
    }

    @AfterEach
    void limpiarPaciente() {
        paciente = null;
    }

    @Test
    @DisplayName("Crear un paciente nuevo persiste y devuelve la entidad")
    void crearPacienteNuevoPersisteYDevuelveLaEntidad() {
        when(pacienteRepository.existsById(100)).thenReturn(false);
        when(pacienteRepository.existsByRutIgnoreCase("19.999.999-9")).thenReturn(false);
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente resultado = pacienteService.crearPaciente(paciente);

        assertEquals("Valentina", resultado.getNombre());
        assertEquals("19.999.999-9", resultado.getRut());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    @DisplayName("No guarda un paciente si el RUT ya existe")
    void crearPacienteConRutRepetidoLanzaConflicto() {
        when(pacienteRepository.existsById(100)).thenReturn(false);
        when(pacienteRepository.existsByRutIgnoreCase("19.999.999-9")).thenReturn(true);

        assertThrows(ConflictoException.class, () -> pacienteService.crearPaciente(paciente));
        verify(pacienteRepository, never()).save(paciente);
    }
}
