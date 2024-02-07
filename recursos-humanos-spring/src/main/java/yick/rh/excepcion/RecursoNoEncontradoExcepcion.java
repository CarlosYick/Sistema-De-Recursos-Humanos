package yick.rh.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//RuntimeException es para no estar obligados a procesar esta excepcion
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoExcepcion extends RuntimeException{
    public RecursoNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}
