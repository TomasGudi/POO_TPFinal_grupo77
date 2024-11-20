package ar.edu.unju.escmi.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "salon_id")
	private Salon salon;
	
	@Column(name = "reserva_fecha")
	private LocalDate fecha;
	
	@Column(name = "reserva_hora_inicio")
	private short horaInicio;
	
	@Column(name = "reserva_hora_fin")
	private short horaFin;
	
	@Column(name = "reserva_monto_pagado")
	private double montoPagado;
	
	@OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ServicioAdicional> servicios = new ArrayList<>();
	
	@Column(name = "reserva_pago_adelantado")
	private double pagoAdelantado;
	
	@Column(name = "reserva_cancelado")
	private boolean cancelado = true;
	
	@Column(name = "reserva_estado")
	private boolean estado;
	
	public Reserva() {
	}

	public Reserva(Cliente cliente, Salon salon, LocalDate fecha, short horaInicio, short horaFin, double montoPagado,
			double pagoAdelantado, boolean cancelado) {
		super();
		this.cliente = cliente;
		this.salon = salon;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.montoPagado = montoPagado;
		this.pagoAdelantado = pagoAdelantado;
		this.cancelado = cancelado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public short getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(short horaInicio) {
		this.horaInicio = horaInicio;
	}

	public short getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(short horaFin) {
		this.horaFin = horaFin;
	}

	public double getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}

	public List<ServicioAdicional> getServicios() {
		return servicios;
	}

	public void setServicios(ServicioAdicional servicio) {
		servicios.add(servicio);
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
	
	public double calcularCostoHorarioExtendido() {
		int horasReservadas = horaFin - horaInicio; 
		int horasExtras = horasReservadas - 4;
		
		if(horasExtras > 0) {
			return horasExtras * 10000;
		}
		return 0;
	}
	
	public double calcularMontoTotal() {
		double costoServicios = (servicios != null?
								servicios.stream()
								.mapToDouble(ServicioAdicional::getPrecio)
								.sum() : 0);
		
		double costoHorarioExtendido = calcularCostoHorarioExtendido();
		return salon.getPrecio() + costoServicios + costoHorarioExtendido;
	}
	
	public double calcularPagoPendiente() {
		return calcularMontoTotal() - pagoAdelantado;
	}
	
	public void mostrarDatos() {
		System.out.println("Reserva: " + id);
		System.out.println("Cliente: " + cliente.getNombre());
		System.out.println("Salon: " + salon.getNombre() + " - Capacidad: " + salon.getCapacidad());
		System.out.println("Fecha: " + fecha);
		System.out.println("Reservado de: " + horaInicio + " - " + horaFin);
		
		if(servicios != null && !servicios.isEmpty()) {
			System.out.println("Servicios adicionales: ");
			servicios.forEach(servicio ->
			System.out.println(" -" + servicio.getDescripcion() + ": $" + servicio.getPrecio()));
		}
		else {
			System.out.println("No se han contratado servicios adicionales");
		}
		
		System.out.println("Monto pagado: $" + montoPagado);
		System.out.println("Costo por horario extendido: $" + calcularCostoHorarioExtendido());
		System.out.println("Monto total: $" + calcularMontoTotal());
		if (cancelado == true) {
			System.out.println("\nCANCELADO");
		}
		else {
			System.out.println("\nPAGO PENDIENTE");
			System.out.println("Pago pendiente: $" + calcularPagoPendiente());
		}
	}
}
