package com.example.karsa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaModel {
    
    private Integer id; 
    private String nombre; 
    private String rfc; 
    private String estatus;
    
}
