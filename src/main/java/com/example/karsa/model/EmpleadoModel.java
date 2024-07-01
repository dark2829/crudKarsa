package com.example.karsa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Este modelo es una representación de la estructura de la "tabla" empleados en mongo.
 * Importa los paquetes Data, Builder, NoArgsConstructor, AllArgsConstructor de Lombok
 * Por ende tiene un constructor empty, constructor con todos los atributos y getter y setter de cada atributo.
 * @author Alejandro Toscuento Flores
 * @version 29/06/2024
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "empleados")
public class EmpleadoModel {
    
    @Id    
    private Integer id;
    @NotBlank(message = "El nombre es requerido")
    private String nombreCompleto;
    private String nombre; 
    @NotBlank(message = "El puesto es requerido")
    private String puesto;
    @NotNull(message = "El salario es requerido")
    @Min(value = 0, message = "El salario debe ser un monto mayor que 0")
    private Double salario; 
    @NotNull(message = "La fecha de ingreso es requerida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso; 
    @NotBlank(message = "El estatus es requerido")
    private String status; 
    @NotBlank(message = "El RFC es requerido")
    @Length(max = 13)
    private String rfc; 
    
}
