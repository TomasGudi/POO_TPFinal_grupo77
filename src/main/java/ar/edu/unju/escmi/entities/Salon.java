package ar.edu.unju.escmi.entities;

import javax.persistence.*;

@Entity
@Table(name = "salones")
public class Salon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "capacidad", nullable = false)
	private int capacidad;

	@Column(name = "precio", nullable = false)
	private double precio;

	@Column(name = "pileta")
	private boolean conPileta;

	public Salon() {
	}

	public Salon(String nombre, int capacidad, boolean conPileta, double precio) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.precio = precio;
		this.conPileta = conPileta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isConPileta() {
		return conPileta;
	}

	public void setConPileta(boolean conPileta) {
		this.conPileta = conPileta;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Salon{" + "ID=" + id + ", Nombre='" + nombre + '\'' + ", Capacidad=" + capacidad + ", Con Pileta="
				+ (conPileta ? "Sí" : "No") + ", Precio=" + precio + '}';
	}
}