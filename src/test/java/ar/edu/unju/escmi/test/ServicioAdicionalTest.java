package ar.edu.unju.escmi.test;

import ar.edu.unju.escmi.dao.IServicioAdicionalDao;
import ar.edu.unju.escmi.dao.imp.ServicioAdicionalDAOImpl;
import ar.edu.unju.escmi.entities.ServicioAdicional;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioAdicionalTest {

    private static IServicioAdicionalDao servicioDAO;

    @BeforeAll
    public static void init() {
        servicioDAO = new ServicioAdicionalDAOImpl();
    }

    @Test
    public void testGuardarServicio() {
        ServicioAdicional servicio = new ServicioAdicional("Catering", 5000.0, true);
        servicioDAO.guardar(servicio);
        assertNotNull(servicio.getId(), "El servicio debería haberse guardado con un ID generado.");
    }

    @Test
    public void testBuscarPorId() {
        ServicioAdicional servicio = new ServicioAdicional("DJ", 3000.0, true);
        servicioDAO.guardar(servicio);

        ServicioAdicional encontrado = servicioDAO.buscarPorId(servicio.getId());
        assertNotNull(encontrado, "El servicio debería haberse encontrado.");
        assertEquals("DJ", encontrado.getDescripcion(), "La descripción del servicio no coincide.");
    }

    @Test
    public void testActualizarServicio() {
        ServicioAdicional servicio = new ServicioAdicional("Decoración", 4000.0, true);
        servicioDAO.guardar(servicio);

        servicio.setPrecio(4500.0);
        servicioDAO.actualizar(servicio);

        ServicioAdicional actualizado = servicioDAO.buscarPorId(servicio.getId());
        assertEquals(4500.0, actualizado.getPrecio(), "El precio debería haberse actualizado.");
    }

    @Test
    public void testObtenerTodosLosServicios() {
        List<ServicioAdicional> servicios = servicioDAO.obtenerTodos();
        assertNotNull(servicios, "La lista de servicios no debería ser nula.");
        assertFalse(servicios.isEmpty(), "La lista de servicios debería contener al menos un elemento.");
    }
}
