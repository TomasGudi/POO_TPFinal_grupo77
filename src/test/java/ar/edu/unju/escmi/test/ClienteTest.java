package ar.edu.unju.escmi.test;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDAOImpl;
import ar.edu.unju.escmi.entities.Cliente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    private IClienteDao clienteDAO = new ClienteDAOImpl();

    @Test
    public void testGuardarCliente() {
        Cliente cliente = new Cliente("Juan", "Perez", "Calle Falsa 123", 12345678, "3881234567", true);
        clienteDAO.guardar(cliente);
        assertNotNull(cliente.getId());
    }

    @Test
    public void testBuscarPorId() {
        Cliente cliente = clienteDAO.buscarPorId(1L);
        assertNotNull(cliente);
    }
}