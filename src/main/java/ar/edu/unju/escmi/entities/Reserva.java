package ar.edu.unju.escmi.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id")
    private List<ServicioAdicional> servicios;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double montoPagado;
    private double pagoAdelantado;
    private boolean cancelado; // Si está completamente pagado
    private boolean estado; // Estado lógico para eliminaciones lógicas

    // Constructor por defecto
    public Reserva() {}

    // Constructor completo
    public Reserva(Cliente cliente, Salon salon, LocalDate fecha, LocalTime horaInicio,
                   LocalTime horaFin, double pagoAdelantado, boolean estado) {
        this.cliente = cliente;
        this.salon = salon;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.montoPagado = pagoAdelantado;
        this.pagoAdelantado = pagoAdelantado;
        this.cancelado = false;
        this.estado = estado;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioAdicional> servicios) {
        this.servicios = servicios;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public double getPagoAdelantado() {
        return pagoAdelantado;
    }

    public void setPagoAdelantado(double pagoAdelantado) {
        this.pagoAdelantado = pagoAdelantado;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" +
               "ID=" + id +
               ", Cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
               ", Salón=" + salon.getNombre() +
               ", Fecha=" + fecha +
               ", Hora Inicio=" + horaInicio +
               ", Hora Fin=" + horaFin +
               ", Monto Pagado=" + montoPagado +
               ", Pago Adelantado=" + pagoAdelantado +
               ", Cancelado=" + (cancelado ? "Sí" : "No") +
               ", Estado=" + (estado ? "Activo" : "Inactivo") +
               '}';
    }
}
