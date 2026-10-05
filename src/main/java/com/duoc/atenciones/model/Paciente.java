package com.duoc.atenciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "PACIENTES")
@Relation(itemRelation = "paciente", collectionRelation = "pacientes")
public class Paciente {

    @Id
    @Column(name = "ID")
    private Integer id;

    @NotBlank
    @Pattern(regexp = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]", message = "El RUT debe tener el formato 12.345.678-9")
    @Column(name = "RUT", nullable = false, unique = true, length = 12)
    private String rut;

    @NotBlank
    @Size(max = 80)
    @Column(name = "NOMBRE", nullable = false, length = 80)
    private String nombre;

    @NotBlank
    @Size(max = 80)
    @Column(name = "APELLIDO", nullable = false, length = 80)
    private String apellido;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener el formato yyyy-MM-dd")
    @Column(name = "FECHA_NACIMIENTO", nullable = false, length = 10)
    private String fechaNacimiento;

    @NotBlank
    @Pattern(regexp = "\\+569\\d{8}", message = "El telefono debe tener el formato +56912345678")
    @Column(name = "TELEFONO", nullable = false, length = 15)
    private String telefono;

    @NotBlank
    @Email
    @Size(max = 120)
    @Column(name = "EMAIL", nullable = false, length = 120)
    private String email;
}
