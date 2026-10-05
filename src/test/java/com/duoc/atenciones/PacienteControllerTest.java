package com.duoc.atenciones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PacienteControllerTest {

    private static final String PACIENTE_NUEVO_JSON = """
            {
              "id": 100,
              "rut": "19.999.999-9",
              "nombre": "Valentina",
              "apellido": "Morales",
              "fechaNacimiento": "1992-08-15",
              "telefono": "+56919999999",
              "email": "valentina.morales@correo.cl"
            }
            """;

    private static final String CONSULTA_NUEVA_JSON = """
            {
              "id": 100,
              "pacienteId": 8,
              "fecha": "2026-08-01",
              "motivo": "Control pediatrico",
              "medico": "Dra. Paula Rios",
              "estado": "agendada"
            }
            """;

    private static final String ATENCION_NUEVA_JSON = """
            {
              "id": 100,
              "pacienteId": 1,
              "consultaId": 1,
              "fecha": "2026-01-10",
              "diagnostico": "Control de seguimiento",
              "tratamiento": "Mantener indicaciones previas",
              "medico": "Dra. Paula Rios"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarPacientesDevuelveJsonConAlMenosOchoRegistros() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pacientes.length()").value(greaterThanOrEqualTo(8)))
                .andExpect(jsonPath("$._embedded.pacientes[0].id").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].rut").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].nombre").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].apellido").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].fechaNacimiento").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].telefono").exists())
                .andExpect(jsonPath("$._embedded.pacientes[0].email").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void obtenerPacientePorIdDevuelveElDetalle() throws Exception {
        mockMvc.perform(get("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Maria"))
                .andExpect(jsonPath("$.rut").value("11.111.111-1"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void obtenerPacienteInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/pacientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerHistorialDevuelvePacienteConsultasYAtenciones() throws Exception {
        mockMvc.perform(get("/pacientes/1/historial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente.id").value(1))
                .andExpect(jsonPath("$.consultas.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.atenciones.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.paciente.href").exists())
                .andExpect(jsonPath("$._links.consultas.href").exists())
                .andExpect(jsonPath("$._links.atenciones.href").exists());
    }

    @Test
    void obtenerHistorialDePacienteInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/pacientes/99/historial"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPacientePorRutDevuelveElDetalle() throws Exception {
        mockMvc.perform(get("/pacientes/rut/11.111.111-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Maria"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPacientePorRutInvalidoDevuelve400() throws Exception {
        mockMvc.perform(get("/pacientes/rut/invalido"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void buscarPacientePorRutInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/pacientes/rut/99.999.999-9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearPacienteDevuelve201YJson() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PACIENTE_NUEVO_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.nombre").value("Valentina"))
                .andExpect(jsonPath("$.rut").value("19.999.999-9"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void crearPacienteConRutInvalidoDevuelve400() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "rut": "invalido",
                                  "nombre": "Valentina",
                                  "apellido": "Morales",
                                  "fechaNacimiento": "1992-08-15",
                                  "telefono": "+56919999999",
                                  "email": "valentina.morales@correo.cl"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearPacienteDuplicadoDevuelve409() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PACIENTE_NUEVO_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PACIENTE_NUEVO_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void modificarPacienteDevuelveJsonActualizado() throws Exception {
        mockMvc.perform(put("/pacientes/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "rut": "18.888.888-8",
                                  "nombre": "Nicolas",
                                  "apellido": "Paredes",
                                  "fechaNacimiento": "2010-04-02",
                                  "telefono": "+56918888888",
                                  "email": "nicolas.paredes@nuevo.cl"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nicolas.paredes@nuevo.cl"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void eliminarPacienteConHistorialDevuelve409() throws Exception {
        mockMvc.perform(delete("/pacientes/1"))
                .andExpect(status().isConflict());
    }

    @Test
    void eliminarPacienteSinHistorialDevuelve204() throws Exception {
        mockMvc.perform(delete("/pacientes/8"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pacientes/8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarConsultasDevuelveAlMenosOchoRegistros() throws Exception {
        mockMvc.perform(get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.consultas.length()").value(greaterThanOrEqualTo(8)))
                .andExpect(jsonPath("$._embedded.consultas[0].motivo").exists())
                .andExpect(jsonPath("$._embedded.consultas[0].estado").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void filtrarConsultasPorPacienteDevuelveLasDelPaciente() throws Exception {
        mockMvc.perform(get("/consultas").param("pacienteId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.consultas.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$._embedded.consultas[0].pacienteId").value(1))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void filtrarConsultasConPacienteIdInvalidoDevuelve400() throws Exception {
        mockMvc.perform(get("/consultas").param("pacienteId", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void filtrarConsultasDePacienteInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/consultas").param("pacienteId", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerConsultaInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/consultas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearYEliminarConsultaDevuelve201Y204() throws Exception {
        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CONSULTA_NUEVA_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motivo").value("Control pediatrico"))
                .andExpect(jsonPath("$._links.self.href").exists());

        mockMvc.perform(delete("/consultas/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarConsultaConAtencionesDevuelve409() throws Exception {
        mockMvc.perform(delete("/consultas/1"))
                .andExpect(status().isConflict());
    }

    @Test
    void listarAtencionesDevuelveAlMenosOchoRegistros() throws Exception {
        mockMvc.perform(get("/atenciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.atenciones.length()").value(greaterThanOrEqualTo(8)))
                .andExpect(jsonPath("$._embedded.atenciones[0].diagnostico").exists())
                .andExpect(jsonPath("$._embedded.atenciones[0].tratamiento").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void filtrarAtencionesPorPacienteDevuelveLasDelPaciente() throws Exception {
        mockMvc.perform(get("/atenciones").param("pacienteId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.atenciones[0].pacienteId").value(2))
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void filtrarAtencionesConPacienteIdInvalidoDevuelve400() throws Exception {
        mockMvc.perform(get("/atenciones").param("pacienteId", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerAtencionInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/atenciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearYEliminarAtencionDevuelve201Y204() throws Exception {
        mockMvc.perform(post("/atenciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ATENCION_NUEVA_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnostico").value("Control de seguimiento"))
                .andExpect(jsonPath("$._links.self.href").exists());

        mockMvc.perform(delete("/atenciones/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    void crearAtencionConConsultaDeOtroPacienteDevuelve400() throws Exception {
        mockMvc.perform(post("/atenciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pacienteId": 2,
                                  "consultaId": 1,
                                  "fecha": "2026-01-10",
                                  "diagnostico": "No corresponde",
                                  "tratamiento": "Revisar asignacion",
                                  "medico": "Dr. Tomas Bravo"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
