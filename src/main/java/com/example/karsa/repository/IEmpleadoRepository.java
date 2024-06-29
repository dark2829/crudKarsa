package com.example.karsa.repository;

import com.example.karsa.model.EmpleadoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends MongoRepository<EmpleadoModel, Integer>{
    
}
