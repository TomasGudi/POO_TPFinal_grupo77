package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;

class ClienteTest {
    private IClienteDao clienteDao;
    private Cliente nuevoCliente;

    @BeforeEach
    void setUp() {
        clienteDao = new ClienteDaoImp();
        nuevoCliente = new Cliente(1, "Juan", "Garzon", "Cuyaya", "3885142211");
        clienteDao.guardarCliente(nuevoCliente);
    }

    @Test
    void testDarDeAltaCliente() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        assertNotNull(clienteConsultado, "El cliente debería haberse dado de alta correctamente");
    }

    @Test
    void testNombreClienteAlta() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        assertEquals("Juan", clienteConsultado.getNombre(), "El nombre del cliente debería coincidir al dar de alta");
    }
@Test
    void testModificarNombreCliente() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        clienteConsultado.setNombre("Juan");
        clienteDao.modificarCliente(clienteConsultado);

        Cliente clienteModificado = clienteDao.consultarCliente(1);
        assertEquals("Juan", clienteModificado.getNombre(), "El nombre debería haberse actualizado correctamente");
    }

    @Test
    void testModificarApellidoCliente() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        clienteConsultado.setApellido("Garzon");
        clienteDao.modificarCliente(clienteConsultado);

        Cliente clienteModificado = clienteDao.consultarCliente(1);
        assertEquals("Garzon", clienteModificado.getApellido(), "El apellido debería haberse actualizado correctamente");
    }

    @Test
    void testNombreEsperadoCliente() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        clienteConsultado.setNombre("Juan");
        clienteDao.modificarCliente(clienteConsultado);

        Cliente clienteModificado = clienteDao.consultarCliente(1);
        assertEquals("Juan", clienteModificado.getNombre(), "El nombre debería coincidir con los datos esperados");
    }

    @Test
    void testApellidoEsperadoCliente() {
        Cliente clienteConsultado = clienteDao.consultarCliente(1);
        clienteConsultado.setApellido("Garzon");
        clienteDao.modificarCliente(clienteConsultado);

        Cliente clienteModificado = clienteDao.consultarCliente(1);
        assertEquals("Garzon", clienteModificado.getApellido(), "El apellido debería coincidir con los datos esperados");
    }
}