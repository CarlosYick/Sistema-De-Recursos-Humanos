package yick.rh.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yick.rh.excepcion.RecursoNoEncontradoExcepcion;
import yick.rh.modelo.Empleado;
import yick.rh.servicio.IEmpleadoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//el RestController nos permite usar este controlador en la fabrica de spring y hacer peticiones de tipo rest con el protocolo http
@RestController
//http://localhost:8080/rh-app/
@RequestMapping("rh-app")
@CrossOrigin(value="http://localhost:3000")
public class EmpleadoControlador {
    //logger es para enviar informacion a consola
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
        var empleados = empleadoServicio.listarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado){
        logger.info("Empleado a agregar: "+ empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: "+id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoRecibido){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: "+id);
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado==null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: "+id);
        empleadoServicio.eliminarEmpleado(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
