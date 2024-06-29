package com.example.karsa.ServicesImp;

import com.example.karsa.ServicesInt.IEmpleadoServicesInt;
import com.example.karsa.ServicesInt.ISecuenciaServiceInt;
import com.example.karsa.model.EmpleadoModel;
import com.example.karsa.repository.IEmpleadoRepository;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            String nombre = getOnlyName(empleado.getNombreCompleto());
            empleado.setNombre(nombre);
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
                respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Empleado no encontrado", empleado);
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al consultar un empleado: "+e);
        }
        return respuesta; 
    }

    @Override
    public Map<String, Object> modificarEmpleado(EmpleadoModel empleadoUpdate, Integer id) {
        Map<String, Object> respuesta = null; 
        try{
            Map<String,Object> empleadoMap = obtenerEmpleadoId(id); 
            if((Boolean) empleadoMap.get("status") ){
                EmpleadoModel empleadoToModificar = (EmpleadoModel) empleadoMap.get("data");
                Integer idOriginal = empleadoToModificar.getId();
                empleadoToModificar = modificaEmpleado(empleadoToModificar, empleadoUpdate);
                empleadoToModificar.setId(idOriginal);
                empleadoToModificar = empleadoRepository.save(empleadoToModificar);
                if(empleadoToModificar != null){
                    respuesta = crearRespuesta(Boolean.TRUE, HttpStatus.OK, "Empleado modificado exitosamente", empleadoToModificar);
                }else{
                    respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Error al modificar el empleado", empleadoToModificar);
                }
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al crear un empleado: "+e);
        }
        return respuesta; 
    }

    @Override
    public Map<String, Object> boorarEmpleado(int id) {
        Map<String, Object> respuesta; 
        try{
            EmpleadoModel empleado = getEmpleado(id);
            Boolean isDeleteOk = eliminarEmpleado(id);
            if(isDeleteOk == true){
                respuesta = crearRespuesta(Boolean.TRUE, HttpStatus.OK, "Empleado eliminado", empleado);
            }else{
                respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Imposible borrar un registro inexistente", null);
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al consultar un empleado: "+e);
        }
        return respuesta; 
    }

    @Override
    public Map<String, Object> buscarEmpleadoNombre(String nombre) {
        Map<String, Object> respuesta; 
        try{
            List<EmpleadoModel> empleados = getNombres(nombre);
            if(Objects.isNull(empleados)){
                respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Imposible consultar un registro inexistente", null);
            }else{
                if(empleados.size() != 0){
                    respuesta = crearRespuestaLista(Boolean.TRUE, HttpStatus.OK, "Empleado eliminado", empleados);
                }else{
                    respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Imposible consultar un registro inexistente", null);
                }
            }
        }catch(Exception e){
            respuesta = crearRespuesta(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Error inminente", null);
            System.out.println("Error inesperado al consultar un empleado: "+e);
        }
        return respuesta; 
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
    
    public Map<String, Object> crearRespuestaLista(Boolean estatus, HttpStatus http, String mensaje, List<EmpleadoModel> empleado){
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
    
    public Boolean eliminarEmpleado(Integer id){
        Boolean isEliminatedOk = false;
        EmpleadoModel empleado = getEmpleado(id);
        if(empleado != null){
            try{
                empleadoRepository.deleteById(id);
                isEliminatedOk = true; 
            }catch(Exception e){
                isEliminatedOk = false; 
                e.printStackTrace();
            }
        }
        return isEliminatedOk;
    }
    
    public EmpleadoModel modificaEmpleado(EmpleadoModel empleadoOriginal, EmpleadoModel empleadoModificado){
        EmpleadoModel empleado = empleadoOriginal;
        Map<String, Object> modificaciones = new HashMap<>();
        
        Class<?> clazz = EmpleadoModel.class;
        Field[] campos = clazz.getDeclaredFields();
        
        try{
            for(Field atributo : campos){
                atributo.setAccessible(true);
                Object original = atributo.get(empleadoOriginal);
                Object modifica = atributo.get(empleadoModificado);
                
                if(original != null && !original.equals(modifica)){
                    atributo.set(empleado, modifica);
                    modificaciones.put(atributo.getName().toString(), modifica);
                }else if(original == null && modifica != null){
                    atributo.set(empleado, modifica);
                    modificaciones.put(atributo.getName().toString(), modifica);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return empleado; 
    }
    
    public List<EmpleadoModel> getNombres(String nombre){
        List<EmpleadoModel> empleados = new ArrayList<>();
        if(nombre != null){
            empleados = empleadoRepository.findByName(nombre);
            if(Objects.isNull(empleados) == true){
                return null;
            }
            if(empleados.size() == 0){
                return null; 
            }
        }
        return empleados; 
    }
    
    public String getOnlyName(String nombre){
        String[] nombres = nombre.split(" ");
        nombre = nombres[0];
        return nombre; 
    }
    
}
