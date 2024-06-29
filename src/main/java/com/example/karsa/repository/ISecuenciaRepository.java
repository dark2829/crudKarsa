package com.example.karsa.repository;

import com.example.karsa.model.SecuenciaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISecuenciaRepository extends MongoRepository<SecuenciaModel, Integer> {
    
}
