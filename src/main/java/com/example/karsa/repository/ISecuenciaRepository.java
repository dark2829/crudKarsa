package com.example.karsa.repository;

import com.example.karsa.model.SecuenciaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que realiza la persistencia de la informacion en la tabla.
 * @author Alejandro Toscuento Flores
 * @see MongoRepository
 * @see SecuenciaModel
 * @version 29/06/2024
 */

@Repository
public interface ISecuenciaRepository extends MongoRepository<SecuenciaModel, Integer> {
    
}
