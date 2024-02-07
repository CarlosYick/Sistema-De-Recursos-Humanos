package yick.rh.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import yick.rh.modelo.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado,Integer> {
}
