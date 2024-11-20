package ar.edu.unju.escmi.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "salones")
public class Salon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salon_id")
	private long id;
	
	@Column(name = "salon_nombre")
	private String nombre;
	
	@Column(name = "salon_capacidad")
	private int capacidad;
	
	@Column(name = "salon_con_pileta")
	private boolean conPileta = false;
	
	@Column(name = "salon_precio", nullable = false)
	private double precio;
	
	@OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
	private List<Reserva> reservas = new ArrayList<>();
	
	public Salon() {
	}

	public Salon(String nombre, int capacidad, boolean conPileta , double precio) {
		super();
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.conPileta = conPileta; 
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Reserva reserva) {
		reservas.add(reserva);
	}

	public void mostrarDatos() {
        System.out.println("\n====== Detalles del Salón =======");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Capacidad: " + capacidad + " personas");
        System.out.println("Pileta: " + (conPileta ? "Sí" : "No"));
        System.out.println("Precio: $" + precio);
    }
}
