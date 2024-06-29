package com.example.karsa.ServicesInt;

import com.example.karsa.model.EmpleadoModel;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IEmpleadoServicesInt {
    public Map<String, Object> crearEmpleado(EmpleadoModel empleado);
    public Map<String, Object> obtenerEmpleadoId(int id);
    public Map<String, Object> modificarEmpleado(EmpleadoModel empleado, Integer id);
    public Map<String, Object> boorarEmpleado(int id);
    public Map<String, Object> buscarEmpleadoNombre(String nombre);
    Map<String, Object> crearRespuesta(Boolean estatus, HttpStatus http, String mensaje, EmpleadoModel empleado);
}
