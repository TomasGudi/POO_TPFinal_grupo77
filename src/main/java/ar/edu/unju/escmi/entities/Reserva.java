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
    @Column(name = "reserva_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "reserva_servicios",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<ServicioAdicional> servicios;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
    
    @Column(name = "pago_adelantado", nullable = false)
    private double pagoAdelantado;
    
    @Column(name = "cancelado", nullable = false)
    private boolean cancelado; 
    
    @Column(name = "estado")
    private boolean estado;

    public Reserva() {}

    public Reserva(Cliente cliente, Salon salon, LocalDate fecha, LocalTime horaInicio,
                   LocalTime horaFin, double pagoAdelantado, List<ServicioAdicional> servicios, boolean estado) {
        this.cliente = cliente;
        this.salon = salon;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.pagoAdelantado = pagoAdelantado;
        this.servicios = servicios;
        this.cancelado = false;
        this.estado = estado;
    }

    public double calcularCostoHorarioExtendido() {
        int horasExtras = horaFin.getHour() - (horaInicio.getHour() + 4); 
        return horasExtras > 0 ? horasExtras * 10000 : 0;
    }

    public double calcularMontoTotal() {
        double costoAdicional = calcularCostoHorarioExtendido();
        double costoServicios = servicios.stream()
                                    .mapToDouble(ServicioAdicional::getPrecio)
                                    .sum();
        return salon.getPrecio() + costoAdicional + costoServicios;
    }

    public double calcularPagoPendiente() {
        return calcularMontoTotal() - pagoAdelantado;
    }
    
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
               "ID =" + id +
               ", Cliente = " + cliente.getNombre() + " " + cliente.getApellido() +
               ", Salón = " + salon.getNombre() +
               ", Fecha = " + fecha +
               ", Hora de inicio = " + horaInicio +
               ", Hora fin = " + horaFin +
               ", Pago Adelantado = $" + pagoAdelantado +
               ", Cancelado = " + (cancelado ? "CANCELADO" : "PAGO PENDIENTE: $" + calcularPagoPendiente()) +
               '}';
    }
}
