package com.example.karsa.controller;

import com.example.karsa.ServicesInt.IEmpleadoServicesInt;
import com.example.karsa.model.EmpleadoModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Aquí se tiene el control de las operaciones REST que se pueden realizar a los empleados.
 * <p>
 * Este controlador maneja solicitudes HTTP bajo la ruta de /gs/empleados
 * </p>
 * @author Alejandro Tocuento Flores
 * @version 29/06/2024
 */

@RestController
@RequestMapping("/gs/empleados")
public class ControllerKarsa {
    
    /**
     * Operaciones que se pueden realizar a los empleados que se definen en {@link IEmpleadoServicesInt}
     * se inyectan mediante spring empleando {@link IEmpleadoServicesInt}
     */
    @Autowired
    private IEmpleadoServicesInt empleadoServiceInt;
    
    /**
     * Peticion de tipo POST para realizar la insersion del empleado en la base de datos.
     * @param empleado Informacion a almacenar en la base de datos.
     * @param binding Para manejar los atributos del objeto y retornar los faltantes.
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @PostMapping(value = "/crear", produces = "application/json")
    public ResponseEntity<Map<String, Object>> crearUsuario(@Valid @RequestBody EmpleadoModel empleado, BindingResult binding) {
        Map<String, Object> data = new HashMap<>();
        
        if(binding.hasErrors()){
            List<String> errores = new ArrayList<>();
            binding.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });
            data.put("data", errores);
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST );
        }
        data = empleadoServiceInt.crearEmpleado(empleado); 
        HttpStatus http = (HttpStatus) data.get("http");
        return new ResponseEntity<>(data, http);
    }
    
    /**
     * Realiza la busqueda de un empleado bajo la ruta "/gs/empleados/1" mediante el metodo GET
     * @param id Identificador del empleado de tipo {@link Integer}
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> obtenerEmpleado(@PathVariable @Min(value = 1, message = "El Id debe ser un número positivo") Integer id){
        Map<String, Object> data = new HashMap<>();
        try{
            data = empleadoServiceInt.obtenerEmpleadoId(id);
        }catch(Exception e){
            String message = "Error parametro no valido";
            data = empleadoServiceInt.crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, message, null);
        }
        HttpStatus http = (HttpStatus) data.get("http");
        return new ResponseEntity<>(data, http);
    } 
    
    /**
     * Realiza una actualizacion de información mediante el metodo PUT en la ruta "/gs/empleados/1/modificar"
     * @param id Identificador del empleado de tipo {@link Integer}
     * @param empleado Informacion a insertar en la tabla de tipo {@link EmpleadoModel}
     * @param binding Para el manejo de informacion faltante.
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @PutMapping(value = "/{id}/modificar", produces = "application/json")
    public ResponseEntity<Map<String, Object>> modificarEmpleado(@PathVariable Integer id,@Valid @RequestBody EmpleadoModel empleado, BindingResult binding){
        Map<String, Object> data = new HashMap<>();
        
        if(binding.hasErrors()){
            List<String> errores = new ArrayList<>();
            binding.getFieldErrors().forEach(error -> {
                errores.add(error.getDefaultMessage());
            });
            data.put("data", errores);
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST );
        }
        data = empleadoServiceInt.modificarEmpleado(empleado, id); 
        HttpStatus http = (HttpStatus) data.get("http");
        return new ResponseEntity<>(data, http);
    }
    
    /**
     * Eliminar un registro existente en la base de datos utilizando el metodo DELETE bajo la ruta "/gs/empleados/1/borrar"
     * @param id Identificador de tipo {@link Integer}
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @DeleteMapping(value = "/{id}/borrar", produces = "application/json")
    public ResponseEntity<Map<String, Object>> borrarEmpleado(@PathVariable Integer id){
        Map<String, Object> data = new HashMap<>();
        try{
            data = empleadoServiceInt.borarEmpleado(id);
        }catch(Exception e){
            String message = "Error parametro no valido";
            data = empleadoServiceInt.crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, message, null);
        }
        HttpStatus http = (HttpStatus) data.get("http");
        return new ResponseEntity<>(data, http);
    }
    
    /**
     * Busca un o una lista de empleados mediante el nombre mediante el metodo POST bajo la ruta "/gs/empleados/bucar?nombre='nombre'"
     * @param nombre Nombre del empleado de tipo {@link String}
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @PostMapping(value = "/buscar", produces = "application/json")
    public ResponseEntity<Map<String, Object>>  buscarEmpleadoNombre(@RequestParam String nombre){
        Map<String, Object> data = new HashMap<>();
        try{
            data = empleadoServiceInt.buscarEmpleadoNombre(nombre);
        }catch(Exception e){
            String message = "Error parametro no valido";
            data = empleadoServiceInt.crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, message, null);
        }
        HttpStatus http = (HttpStatus) data.get("http");
        return new ResponseEntity<>(data, http);
    }    
    
}
