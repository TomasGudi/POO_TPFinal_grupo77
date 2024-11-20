package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.Reserva;
import ar.edu.unju.escmi.entities.Salon;

public class ReservaPagoTest {
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(1, "Benjamin", "Cadena", "Centro", "3881231231");
        Salon salon = new Salon("Salon Grupo77", 20, false, 10000);
        short hi = 10;
        short hf = 14;
        reserva = new Reserva(cliente, salon, LocalDate.now(), hi, hf, 0.0, 10000, true);
    }

    @Test
    void testCalculoMontoTotal() {
        double montoTotal = reserva.calcularMontoTotal();
        assertEquals(10000, montoTotal, "El monto total de la reserva debería ser 10000");
    }

    @Test
    void testCalculoPagoAdelantado() {
        double pagoAdelantado = reserva.getPagoAdelantado();
        assertEquals(10000, pagoAdelantado, "El pago adelantado debería ser 10000");
    }

    @Test
    void testCalculoPagoPendiente() {
        double montoPendiente = reserva.calcularPagoPendiente();
        assertEquals(0, montoPendiente, "El monto pendiente debería ser 0");
    }

    @Test
    void testPagoTotalCancelacion() {
        double pagoAdelantado = reserva.getPagoAdelantado();
        reserva.setMontoPagado(pagoAdelantado);
        reserva.calcularPagoPendiente();

        assertTrue(reserva.isCancelado(), "La reserva debería estar marcada como cancelada después de pagar el monto total");
    }

    @Test
    void testMontoPagado() {
        double pagoAdelantado = reserva.getPagoAdelantado();
        reserva.setMontoPagado(pagoAdelantado);
        reserva.calcularPagoPendiente();

        assertEquals(10000, reserva.getMontoPagado(), "El monto pagado debería coincidir con el monto total después del pago completo");
    }
}