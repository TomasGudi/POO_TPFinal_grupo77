package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IReservaDao;
import ar.edu.unju.escmi.entities.Reserva;

public class ReservaDaoImp implements IReservaDao {
	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void guardarReserva(Reserva reserva) {
		try {
			manager.getTransaction().begin();
			manager.merge(reserva);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction()!=null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo guardar la reserva");
		}
	}

	@Override
	public void mostrarTodosLasReservas() {
		Query query = manager.createQuery("SELECT e FROM Reserva e",Reserva.class);
		@SuppressWarnings("unchecked")
		List<Reserva> reservas = query.getResultList();
		for(Reserva reserva : reservas) {
			reserva.mostrarDatos();
		}
	}

	@Override
	public Reserva consultarReserva(long idReserva) {
		return manager.find(Reserva.class, idReserva);
	}
}
