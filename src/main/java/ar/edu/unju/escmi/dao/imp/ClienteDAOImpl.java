package ar.edu.unju.escmi.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDAOImpl implements IClienteDao {
    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardar(Cliente cliente) {
        manager.getTransaction().begin();
        manager.persist(cliente);
        manager.getTransaction().commit();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return manager.find(Cliente.class, id);
    }

    @Override
    public void actualizar(Cliente cliente) {
        manager.getTransaction().begin();
        manager.merge(cliente);
        manager.getTransaction().commit();
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = manager.find(Cliente.class, id);
        if (cliente != null) {
            manager.getTransaction().begin();
            cliente.setEstado(false); // Eliminación lógica
            manager.merge(cliente);
            manager.getTransaction().commit();
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        TypedQuery<Cliente> query = manager.createQuery("FROM Cliente WHERE estado = true", Cliente.class);
        return query.getResultList();
    }
}