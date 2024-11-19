package ar.edu.unju.escmi.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IReservaDao;
import ar.edu.unju.escmi.entities.Reserva;

public class ReservaDAOImpl implements IReservaDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardar(Reserva reserva) {
        try {
            manager.getTransaction().begin();
            manager.persist(reserva);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar la reserva", e);
        }
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return manager.find(Reserva.class, id);
    }

    @Override
    public void actualizar(Reserva reserva) {
        try {
            manager.getTransaction().begin();
            manager.merge(reserva);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al actualizar la reserva", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            manager.getTransaction().begin();
            Reserva reserva = manager.find(Reserva.class, id);
            if (reserva != null) {
                reserva.setEstado(false);
                manager.merge(reserva);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar la reserva", e);
        }
    }

    @Override
    public List<Reserva> obtenerTodos() {
        TypedQuery<Reserva> query = manager.createQuery("FROM Reserva WHERE estado = true", Reserva.class);
        return query.getResultList();
    }
}
