package ar.edu.unju.escmi.entities;

import javax.persistence.*;

@Entity
@Table(name = "servicios_adicionales")
public class ServicioAdicional {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "estado", nullable = false)
    private boolean estado;

	public ServicioAdicional() {
	}

	public ServicioAdicional(String descripcion, double precio, boolean estado) {
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "ServicioAdicional{" + "ID=" + id + ", Descripción='" + descripcion + '\'' + ", Precio=" + precio
				+ ", Estado=" + (estado ? "Activo" : "Inactivo") + '}';
	}
}