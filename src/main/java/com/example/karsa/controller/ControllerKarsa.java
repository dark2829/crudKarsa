package com.example.karsa.controller;

import com.example.karsa.ServicesInt.IEmpleadoServicesInt;
import com.example.karsa.model.EmpleadoModel;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("/gs/empleados")
public class ControllerKarsa {
    
    @Autowired
    private IEmpleadoServicesInt empleadoServiceInt;
    
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

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> obtenerEmpleado(@RequestParam int idEmpleado){
        ResponseEntity<String> respuesta = null; 
        return respuesta; 
    } 
    
    @PutMapping(value = "/{id}/modificar", produces = "application/json")
    public ResponseEntity<String> modificarEmpleado(@RequestParam int idEmpleado, @RequestBody String empleado){
        ResponseEntity<String> respuesta = null;
        return respuesta; 
    }
    
    @DeleteMapping(value = "/{id}/borrar", produces = "application/json")
    public ResponseEntity<String> borrarEmpleado(@RequestParam int idEmpleado){
        ResponseEntity<String> respuesta = null; 
        return respuesta; 
    }
    
    @PostMapping(value = "/buscar", produces = "application/json")
    public ResponseEntity<String>  buscarEmpleadoNombre(@RequestParam String nombre){
        ResponseEntity<String> respuesta = null; 
        return respuesta; 
    }    
    
}
