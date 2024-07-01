package com.example.karsa.ServicesImp;

import com.example.karsa.ServicesInt.IEmpleadoServicesInt;
import com.example.karsa.ServicesInt.ISecuenciaServiceInt;
import com.example.karsa.model.EmpleadoModel;
import com.example.karsa.repository.IEmpleadoRepository;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Capa de tratamiento de los datos que implementa la interfaz {@link IEmpleadoServicesInt}
 * En esta clase se realiza una sobreescritura de los métodos.
 * @author Alejandro Toscuento Flores
 * @see IEmpleadoServicesInt
 * @see IEmpleadoRepository
 * @see ISecuenciaServiceInt
 * @version 29/06/2024
 */

@Service
public class EmpleadoServicesImp implements IEmpleadoServicesInt{
    
    /**
     * Repositorio de empleados para realizar las acciones de persistencia.
     * Este campo inyectado por spring mediante la anotacion {@link Autowired}
     */
    @Autowired
    private IEmpleadoRepository empleadoRepository;
    
    /**
     * Interfaz en la cual se define el metodo para la generacion de seucuencias unicas.
     * Este campo inyectado por spring mediante la anotacion {@link Autowired}
     */
    @Autowired
    private ISecuenciaServiceInt secuenciaServices; 
    
    /**
     * Crea un empleado nuevo y lo almacena en la base de datos.
     * <p>
     * Este metodo utiliza el servicio de secuencia para obtener un id unico.
     * Obtiene solo el nombre del nombre completo y lo agrega en el campo especifico del objeto.
     * Realiza la insercion mediante {@link IEmpleadoRepository}
     * Crea una respuesta apropiada independientemente de si el empleado se registro o no.
     * </p>
     * @param empleado Un objeto de tipo {@link EmpleadoModel} el cual no contiene:
     * <ul>
     * <li>id</li>
     * <li>nombre</li>
     * </ul>
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
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

    /**
     * Obtiene el empleado mediante el id y retorna una respuesta adecuada, dependiendo del resultado de la operación.
     * @param id Identificador del empleado de tipo {@link Integer}.
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
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
    
    /**
     * Realiza la actualizacion del empleado mediante el empleado recibido.
     * <p>
     * Realiza la busqueda del empleado mediante el id utilizando el metodo {@link #obtenerEmpleadoId(int)}
     * Revisa que el empleado exista dentro de los registros.
     * Crea un empleado temporal.
     * Realiza una actualizacion de los datos modificados contenidos en el parametro de empleado.
     * Le coloca el id original.
     * Realiza la actualizacion mediante {@link IEmpleadoRepository}
     * </p>
     * @param empleadoUpdate Recibe un objeto de tipo {@link EmpleadoModel} que contiene las modificaciones a realizar.
     * @param id El id del empleado de tipo {@link Integer}
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
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
    
    /**
     * Elimina un registro existente en la base de datos.
     * <p>
     * Busca si el empleado existe dentro de los registros.
     * Si el empleado existe realiza la eliminacion mediante {@link IEmpleadoRepository}
     * </p>
     * @param id Identificador del empleado de tipo {@link Integer}
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
    @Override
    public Map<String, Object> borarEmpleado(int id) {
        Map<String, Object> respuesta; 
        try{
            EmpleadoModel empleado = getEmpleado(id);
            Boolean isDeleteOk = eliminarEmpleado(id, empleado);
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
    
    /**
     * Realiza una busqueda de un registro existente mediante el nombre proporcionado.
     * Si existe al menos un elemento regresa una lista de objetos que coincidan con ese nombre.
     * @param nombre
     * @return Devuelve una respuesta estructurada de la sigueinte manera:
     * <ul>
     * <li>{@link Boolean} indicando si el proceso fue exitoso</li>
     * <li>{@link HttpStatus} indicando el verbo de la operacion</li>
     * <li>{@link String} un mensaje descriptivo</li>
     * <li>{@link EmpleadoModel} la informacion del empleado</li>
     * </ul>
     */
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
    
    public Boolean eliminarEmpleado(Integer id, EmpleadoModel empleado){
        Boolean isEliminatedOk = false;
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
