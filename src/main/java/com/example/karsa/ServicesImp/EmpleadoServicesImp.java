package com.example.karsa.ServicesImp;

import com.example.karsa.ServicesInt.IEmpleadoServicesInt;
import com.example.karsa.ServicesInt.ISecuenciaServiceInt;
import com.example.karsa.model.EmpleadoModel;
import com.example.karsa.repository.IEmpleadoRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServicesImp implements IEmpleadoServicesInt{
    
    @Autowired
    private IEmpleadoRepository empleadoRepository;
    
    @Autowired
    private ISecuenciaServiceInt secuenciaServices; 
    
    @Override
    public Map<String, Object> crearEmpleado(EmpleadoModel empleado) {
        Map<String, Object> respuesta = null; 
        try{
            Integer id = secuenciaServices.getSecuencia();
            empleado.setId(id);
            EmpleadoModel empleadoCreado = empleadoRepository.save(empleado); 
            if(empleadoCreado != null){
                respuesta = crearRespuesta(Boolean.TRUE, HttpStatus.OK, "Empleado creado exitosamente", empleadoCreado);
            }else{
                respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Error al crear el empleado", empleadoCreado);
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al crear un empleado: "+e);
        }
        return respuesta; 
    }

    @Override
    public Map<String, Object> obtenerEmpleadoId(int id) {
        Map<String, Object> respuesta; 
        try{
            EmpleadoModel empleado = getEmpleado(id);
            if(empleado != null){
                respuesta = crearRespuesta(Boolean.TRUE, HttpStatus.OK, "Empleado encontrado", empleado);
            }else{
                respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Empleado encontrado", empleado);
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al consultar un empleado: "+e);
        }
        return respuesta; 
    }

    @Override
    public ResponseEntity<?> modificarEmpleado(EmpleadoModel empleado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResponseEntity<?> boorarEmpleado(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EmpleadoModel buscarEmpleadoNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Map<String, Object> crearRespuesta(Boolean estatus, HttpStatus http, String mensaje, EmpleadoModel empleado){
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", estatus);
        respuesta.put("http", http);
        respuesta.put("message", mensaje);
        respuesta.put("data", empleado);
        return respuesta; 
    }
    
    public EmpleadoModel getEmpleado(Integer id){
        EmpleadoModel empleado = null; 
        List<EmpleadoModel> empleados = empleadoRepository.findAll();
        for(EmpleadoModel empleadoIterador : empleados){
            if(empleadoIterador.getId() == id){
                empleado = empleadoIterador;
            }
        }
        return empleado;
    }
    
}
