package ar.edu.unju.escmi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "servicios")
public class ServicioAdicional {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "servicio_id")
	private long id;
	
	@Column(name = "sevicio_descripcion")
	private String descripcion;
	
	@Column(name = "servicio_precio", nullable = false)
	private double precio;
	
	@Column(name = "servicio_estado")
	private boolean estado = false;
	
	@ManyToOne
	@JoinColumn(name = "reserva_id")
	private Reserva reserva;
	
	public ServicioAdicional() {
	}

	public ServicioAdicional(String descripcion, double precio) {
		super();
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	

	public void mostrarDatos() {
        System.out.println("\n====== Detalles del Servicio Adicional =======");
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Precio: $" + precio);
    }
}
