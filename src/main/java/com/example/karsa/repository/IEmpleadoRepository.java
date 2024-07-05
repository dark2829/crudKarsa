package com.example.karsa.repository;

import com.example.karsa.model.EmpleadoModel;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que extiende de MongoRepository donde ya se define un CRUD para realizar las operaciones básicas.
 * En esta clase se realizan las operaciones de persistencia de datos.
 * @author Alejandro Toscuento Flores
 * @see MongoRepository
 * @version 29/06/2024
 */

@Repository
public interface IEmpleadoRepository extends MongoRepository<EmpleadoModel, Integer>{
    
    /**
     * Consulta para encontrar todos los nombres que sean identicos al parametro.
     * @param nombre Recibe el String del nombre para realizar la busqueda.
     * @return devuelve una lista de objetos tipo EmpleadoModel que coincidan con el nombre.
     */
    @Query("{ 'nombre' : ?0 }")
    List<EmpleadoModel> findByName(String nombre);
    /**
     * Consulta el rfc para revisar si existe algun empleado con un rfc existente.
     * @param nombre Recibe el {@link String} del nombre para realizar la busquda
     * @return devuelve una lista, objeto o un elemento nulo en caso de no coincidir el elemento
     */
    @Query("{ 'rfc': ?0 }")
    List<EmpleadoModel> findByRFC(String nombre);
    
    @Query("{ $and: [ " +
           "{ $or: [ { 'nombre': { $exists: ?0 } }, { 'nombre': ?0 } ] }, " +
           "{ $or: [ { 'id': { $exists: ?1 } }, { 'id': { $eq: ?1 } } ] }, " +
           "{ $or: [ { 'rfc': { $exists: ?2 } }, { 'rfc': ?2 } ] }, " +
           "{ $or: [ { 'status': { $exists: ?3 } }, { 'status': ?3 } ] } ] }"
    )
    List<EmpleadoModel> buscarEmpleadosConOr(Integer id, String nombre, String rfc, String estatus);
}
