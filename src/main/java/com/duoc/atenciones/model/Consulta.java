package com.duoc.atenciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONSULTAS")
public class Consulta {

    @Id
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Min(1)
    @Column(name = "PACIENTE_ID", nullable = false)
    private Integer pacienteId;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato yyyy-MM-dd")
    @Column(name = "FECHA", nullable = false, length = 10)
    private String fecha;

    @NotBlank
    @Size(max = 200)
    @Column(name = "MOTIVO", nullable = false, length = 200)
    private String motivo;

    @NotBlank
    @Size(max = 120)
    @Column(name = "MEDICO", nullable = false, length = 120)
    private String medico;

    @NotBlank
    @Size(max = 30)
    @Column(name = "ESTADO", nullable = false, length = 30)
    private String estado;
}
