package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.ServicioAdicional;
import java.util.List;

public interface IServicioAdicionalDao {
    void guardar(ServicioAdicional servicio);
    ServicioAdicional buscarPorId(Long id);
    void actualizar(ServicioAdicional servicio);
    List<ServicioAdicional> obtenerTodos();
}