package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {
	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void guardarCliente(Cliente cliente) {
		try {
			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction()!=null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo guardar el cliente");
		}
	}

	@Override
	public void modificarCliente(Cliente cliente) {
		try {
			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction()!=null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo modificar los datos del cliente");
		}
	}

	@Override
	public void mostrarTodosLosClientes() {
		Query query = manager.createQuery("SELECT e FROM Cliente e",Cliente.class);
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = query.getResultList();
		for(Cliente cliente : clientes) {
			cliente.mostrarCliente();
		}
	}

	@Override
	public Cliente consultarCliente(long idCliente) {
		return manager.find(Cliente.class, idCliente);
	}
}
