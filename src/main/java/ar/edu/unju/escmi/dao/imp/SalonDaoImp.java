package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.ISalonDao;
import ar.edu.unju.escmi.entities.Salon;

public class SalonDaoImp implements ISalonDao {
	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void mostrarLosSalones() {
		Query query = manager.createQuery("SELECT e FROM Salon e",Salon.class);
		@SuppressWarnings("unchecked")
		List<Salon> salones = query.getResultList();
		for(Salon salon : salones) {
			salon.mostrarDatos();
		}
	}

	@Override
	public Salon consultarSalon(long idSalon) {
		return manager.find(Salon.class, idSalon);
	}
	
	
	@Override
	public void guardarSalon(Salon salon) {
        try {
            manager.getTransaction().begin();
            manager.merge(salon);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("Error al guardar el salón: " + e.getMessage());
        }
    }
}
