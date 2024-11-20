package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Reserva;

public interface IReservaDao {
	public void guardarReserva(Reserva reserva);
	public void mostrarTodosLasReservas();
	public Reserva consultarReserva(long idReserva);
}
