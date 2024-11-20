package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Salon;

public interface ISalonDao {
	public void mostrarLosSalones();
	public Salon consultarSalon(long idSalon);
	public void guardarSalon(Salon salon);
}
