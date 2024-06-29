package com.example.karsa.ServicesInt;

import com.example.karsa.model.EmpleadoModel;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface IEmpleadoServicesInt {
    public Map<String, Object> crearEmpleado(EmpleadoModel empleado);
    public EmpleadoModel obtenerEmpleadoId(int id);
    public ResponseEntity<?> modificarEmpleado(EmpleadoModel empleado);
    public ResponseEntity<?> boorarEmpleado(int id);
    public EmpleadoModel buscarEmpleadoNombre(String nombre);
}
