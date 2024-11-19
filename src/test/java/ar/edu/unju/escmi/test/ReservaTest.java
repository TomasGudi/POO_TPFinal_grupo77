package ar.edu.unju.escmi.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unju.escmi.dao.IReservaDao;
import ar.edu.unju.escmi.dao.imp.ReservaDAOImpl;
import ar.edu.unju.escmi.entities.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaTest {

    private static IReservaDao reservaDAO;

    @BeforeAll
    public static void init() {
        reservaDAO = new ReservaDAOImpl();
    }

    @Test
    public void testGuardarReserva() {
        Cliente cliente = new Cliente("Juan", "Pérez", "Calle Falsa 123", 12345678, "3881234567", true);
        Salon salon = new Salon("Salón Principal", 100, true, 15000);
        Reserva reserva = new Reserva(cliente, salon, LocalDate.now(), LocalTime.of(18, 0), LocalTime.of(23, 0), 5000, null, true);

        reservaDAO.guardar(reserva);
        assertNotNull(reserva.getId());
    }

    @Test
    public void testBuscarPorId() {
        Reserva reserva = reservaDAO.buscarPorId(1L);
        assertNotNull(reserva);
    }

    @Test
    public void testActualizarReserva() {
        Reserva reserva = reservaDAO.buscarPorId(1L);
        reserva.setPagoAdelantado(6000);
        reservaDAO.actualizar(reserva);

        Reserva updatedReserva = reservaDAO.buscarPorId(1L);
        assertEquals(6000, updatedReserva.getPagoAdelantado());
    }

    @Test
    public void testEliminarReserva() {
        reservaDAO.eliminar(1L);
        Reserva reserva = reservaDAO.buscarPorId(1L);
        assertNull(reserva);
    }
}
