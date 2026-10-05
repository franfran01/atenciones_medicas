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
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ATENCIONES")
@Relation(itemRelation = "atencion", collectionRelation = "atenciones")
public class Atencion {

    @Id
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Min(1)
    @Column(name = "PACIENTE_ID", nullable = false)
    private Integer pacienteId;

    @NotNull
    @Min(1)
    @Column(name = "CONSULTA_ID", nullable = false)
    private Integer consultaId;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato yyyy-MM-dd")
    @Column(name = "FECHA", nullable = false, length = 10)
    private String fecha;

    @NotBlank
    @Size(max = 200)
    @Column(name = "DIAGNOSTICO", nullable = false, length = 200)
    private String diagnostico;

    @NotBlank
    @Size(max = 300)
    @Column(name = "TRATAMIENTO", nullable = false, length = 300)
    private String tratamiento;

    @NotBlank
    @Size(max = 120)
    @Column(name = "MEDICO", nullable = false, length = 120)
    private String medico;
}
