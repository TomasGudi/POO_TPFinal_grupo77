package ar.edu.unju.escmi.dao;


import ar.edu.unju.escmi.entities.Reserva;
import ar.edu.unju.escmi.entities.Salon;
import ar.edu.unju.escmi.exceptions.SalonNoDisponibleException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IReservaDao {
    void guardar(Reserva reserva);
    Reserva buscarPorId(Long id);
    void actualizar(Reserva reserva);
    void eliminar(Long id);
    List<Reserva> obtenerTodos();
    void verificarDisponibilidad(Salon salon, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) throws SalonNoDisponibleException;
}
