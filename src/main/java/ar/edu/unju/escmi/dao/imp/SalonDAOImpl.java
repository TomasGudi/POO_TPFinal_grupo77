package ar.edu.unju.escmi.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.ISalonDao;
import ar.edu.unju.escmi.entities.Salon;

public class SalonDAOImpl implements ISalonDao {
    private EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardar(Salon salon) {
        try {
            manager.getTransaction().begin();
            manager.persist(salon);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar el salón", e);
        }
    }

    @Override
    public Salon buscarPorId(Long id) {
        try {
            return manager.find(Salon.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el salón con ID " + id, e);
        }
    }

    @Override
    public void actualizar(Salon salon) {
        try {
            manager.getTransaction().begin();
            manager.merge(salon);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar el salón", e);
        }
    }

    @Override
    public List<Salon> obtenerTodos() {
        try {
            TypedQuery<Salon> query = manager.createQuery("FROM Salon", Salon.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los salones", e);
        }
    }
}