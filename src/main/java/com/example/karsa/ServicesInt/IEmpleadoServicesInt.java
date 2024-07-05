package com.example.karsa.ServicesInt;

import com.example.karsa.model.BusquedaModel;
import com.example.karsa.model.EmpleadoModel;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 * Interfaz donde se declaran todos los métodos que puede realizar el sistema para un empleado.
 * @author Alejandro Toscuento Flores
 * @version 29/06/2024
 */

public interface IEmpleadoServicesInt {
    /**
     * Recibe un empleado y lo inserta en la base de datos.
     * @param empleado Objeto de EmpleadoModel para ser insertado en la tabla.
     * @return Respuesta de éxito o fallo, en formato JSON JSON estructurado de la siguiente forma: estatus {@link Boolean}, {@link HttpStatus},{@link String}, {@link EmpleadoModel}.
     */
    public Map<String, Object> crearEmpleado(EmpleadoModel empleado);
    /**
     * Busca un empleado mediante el, id que recibe.
     * @param id Número tipo int no negativo que permite realizar la búsqueda.
     * @return Devuelve un único empleado de tipo EmpleadoModel que coincida con el, Id en formato JSON estructurado de la siguiente forma: estatus {@link Boolean}, {@link HttpStatus},{@link String}, {@link EmpleadoModel}.
     */
    public Map<String, Object> obtenerEmpleadoId(int id);
    /**
     * Realiza la modificación de un empleado existente.
     * Si el empleado no existe, retorna una respuesta con un mensaje significativo.
     * @param empleado Objeto de EmpleadoModel para realizar la modificación del empleado existente. 
     * @param id Número tipo int no negativo para realizar la búsqueda del empleado.
     * @return Devuelve un único empleado de tipo EmpleadoModel que coincida con el, Id en formato JSON estructurado de la siguiente forma: estatus {@link Boolean}, {@link HttpStatus},{@link String}, {@link EmpleadoModel}.
     */
    public Map<String, Object> modificarEmpleado(EmpleadoModel empleado, Integer id);
    /**
     * Realiza la eliminación de un único registro que coincida con el, id.
     * Si el ID no se encuentra, retorna un mensaje significativo.
     * @param id Número tipo int no negativo para realizar la búsqueda del empleado.
     * @return Devuelve el empleado de tipo EmpleadoModel que fue eliminado mediante el, id en formato JSON estructurado de la siguiente forma: estatus {@link Boolean}, {@link HttpStatus},{@link String}, {@link EmpleadoModel}.
     */
    public Map<String, Object> borarEmpleado(int id);
    /**
     * Realiza una consulta mediante el nombre del empleado o empleados y regresa un objeto o una lista de coincidencias.
     * @param nombre Nombre de tipo String para realizar la búsqueda del empleado.
     * @return Devuelve un empleado o una lista de empleados de tipo EmpleadoModel dentro de un objeto tipo JSON estructurado de la siguiente forma: estatus {@link Boolean}, {@link HttpStatus},{@link String}, {@link EmpleadoModel}.
     */
    public Map<String, Object> buscarEmpleadoNombre(String nombre);
    /**
     * Crea una estructura para devolver una respuesta entendible para el cliente.
     * @param estatus Tipo Boolean para indicar si el proceso fue exitoso.
     * @param http Tipo HTTP que el cliente pueda comprender.
     * @param mensaje tipo String que pueda ser mostrada como información específica.
     * @param empleado Tipo EmpleadoModel o null en caso de cumplir para mostrar la insersion, búsquedas o modificaciones realizadas.
     * @return Devuelve un objeto tipo Map con las siguientes claves, status, http, message, data.
     */
    public Map<String, Object> crearRespuesta(Boolean estatus, HttpStatus http, String mensaje, EmpleadoModel empleado);
    
    /**
     * Consulta todos los empleados
     * @return una lista de empleados dentro de object
     */
    public Map<String, Object> obtenerEmpleados();
    
    public Map<String, Object> obtenerEmpleadosCriterios(BusquedaModel empleado);
    
}
