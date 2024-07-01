package com.example.karsa.ServicesInt;

import com.example.karsa.model.SecuenciaModel;

/**
 * Interfaz que gestiona las operaciones que se pueden realizar a la tabla de secuencias.
 * @author Alejandro Toscuento Flores
 * @see SecuenciaModel
 * @version 29/06/2024
 */

public interface ISecuenciaServiceInt {
    
    /**
     * Obtiene la secuencia actual
     * @return Devuelve un {@link Integer}
     */
    public Integer getSecuencia();
    
    /**
     * Realiza una actualziacion de la secuencia en caso de no existir la inserta.
     * @param secuencia 
     */
    public void saveOrUpdateSecuencia(SecuenciaModel secuencia); 
}
