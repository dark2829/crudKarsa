package com.example.karsa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Objeto que modela la tabla secuencia para tener un control de ids
 * @author Alejandro Toscuento Flores
 * @version 29/06/2024
 * @see NoArgsConstructor
 * @see AllArgsConstructor
 * @see Data
 * @see Builder
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "secuencia")
public class SecuenciaModel {
    
    @Id
    private Integer id; 
    private Integer secuencia; 
    
}
