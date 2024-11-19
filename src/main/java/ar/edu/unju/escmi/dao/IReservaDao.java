package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Reserva;
import java.util.List;

public interface IReservaDao {
    void guardar(Reserva reserva);
    Reserva buscarPorId(Long id);
    void actualizar(Reserva reserva);
    void eliminar(Long id);
    List<Reserva> obtenerTodos();
}
