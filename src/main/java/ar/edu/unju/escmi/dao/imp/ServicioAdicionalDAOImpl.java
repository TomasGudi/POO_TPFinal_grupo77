package ar.edu.unju.escmi.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IServicioAdicionalDao;
import ar.edu.unju.escmi.entities.ServicioAdicional;

public class ServicioAdicionalDAOImpl implements IServicioAdicionalDao {
    private EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardar(ServicioAdicional servicio) {
        try {
            manager.getTransaction().begin();
            manager.persist(servicio);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar el servicio adicional", e);
        }
    }

    @Override
    public ServicioAdicional buscarPorId(Long id) {
        try {
            return manager.find(ServicioAdicional.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el servicio adicional con ID " + id, e);
        }
    }

    @Override
    public void actualizar(ServicioAdicional servicio) {
        try {
            manager.getTransaction().begin();
            manager.merge(servicio);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar el servicio adicional", e);
        }
    }

    @Override
    public List<ServicioAdicional> obtenerTodos() {
        try {
            TypedQuery<ServicioAdicional> query = manager.createQuery("FROM ServicioAdicional", ServicioAdicional.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los servicios adicionales", e);
        }
    }
}