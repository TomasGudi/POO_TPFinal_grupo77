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
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private long id;
	
	@Column(name = "cliente_dni", nullable = false)
	private int dni;
	
	@Column(name = "cliente_nombre")
	private String nombre;
	
	@Column(name = "cliente_apellido")
	private String apellido;

	@Column(name = "cliente_domicilio")
	private String domicilio;
	
	@Column(name= "cliente_telefono", nullable = false)
	private String telefono;
	
	@Column(name = "cliente_estado")
	private boolean estado = true;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Reserva> reservas = new ArrayList<>();

	public Cliente() {
	}
	
	public Cliente(int dni, String nombre, String apellido, String domicilio, String telefono) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.telefono = telefono;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Reserva reserva) {
		reservas.add(reserva);
	}

	public void mostrarCliente() {
		System.out.println("\nCliente: " + id);
		System.out.println("Nombre del cliente: " + apellido + ", " + nombre);
		System.out.println("DNI: " + dni);
		System.out.println("Domicilio: " + domicilio);
		System.out.println("Telefono: " + telefono);
	}
}
