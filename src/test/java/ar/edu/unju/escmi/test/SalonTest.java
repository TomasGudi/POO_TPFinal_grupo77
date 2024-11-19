package ar.edu.unju.escmi.test;

import ar.edu.unju.escmi.dao.ISalonDao;
import ar.edu.unju.escmi.dao.imp.SalonDAOImpl;
import ar.edu.unju.escmi.entities.Salon;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalonTest {

    private static ISalonDao salonDAO;

    @BeforeAll
    public static void init() {
        salonDAO = new SalonDAOImpl();
    }

    @Test
    public void testGuardarSalon() {
        Salon salon = new Salon("Salón Fiesta", 100, true, 15000.0);
        salonDAO.guardar(salon);
        assertNotNull(salon.getId(), "El salón debería haberse guardado con un ID generado.");
    }

    @Test
    public void testBuscarPorId() {
        Salon salon = new Salon("Salón Elegante", 80, false, 12000.0);
        salonDAO.guardar(salon);

        Salon encontrado = salonDAO.buscarPorId(salon.getId());
        assertNotNull(encontrado, "El salón debería haberse encontrado.");
        assertEquals("Salón Elegante", encontrado.getNombre(), "El nombre del salón no coincide.");
    }

    @Test
    public void testActualizarSalon() {
        Salon salon = new Salon("Salón Moderno", 150, true, 20000.0);
        salonDAO.guardar(salon);

        salon.setCapacidad(200);
        salon.setPrecio(25000.0);
        salonDAO.actualizar(salon);

        Salon actualizado = salonDAO.buscarPorId(salon.getId());
        assertEquals(200, actualizado.getCapacidad(), "La capacidad debería haberse actualizado.");
        assertEquals(25000.0, actualizado.getPrecio(), "El precio debería haberse actualizado.");
    }

    @Test
    public void testObtenerTodosLosSalones() {
        List<Salon> salones = salonDAO.obtenerTodos();
        assertNotNull(salones, "La lista de salones no debería ser nula.");
        assertFalse(salones.isEmpty(), "La lista de salones debería contener al menos un elemento.");
    }
}