package com.example.karsa.repository;

import com.example.karsa.model.EmpleadoModel;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends MongoRepository<EmpleadoModel, Integer>{
    
    @Query("{ 'nombre' : ?0 }")
    List<EmpleadoModel> findByName(String nombre);
}
