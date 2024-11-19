package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Salon;
import java.util.List;

public interface ISalonDao {
    void guardar(Salon salon);
    Salon buscarPorId(Long id);
    void actualizar(Salon salon);
    List<Salon> obtenerTodos();
}