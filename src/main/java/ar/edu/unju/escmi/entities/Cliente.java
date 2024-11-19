package ar.edu.unju.escmi.entities;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String domicilio;
    private int dni;
    private String telefono;
    private boolean estado;

    public Cliente() {}

    public Cliente(String nombre, String apellido, String domicilio, int dni, String telefono, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.dni = dni;
        this.telefono = telefono;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Cliente{" +
               "ID=" + id +
               ", Nombre='" + nombre + '\'' +
               ", Apellido='" + apellido + '\'' +
               ", Domicilio='" + domicilio + '\'' +
               ", DNI=" + dni +
               ", Teléfono='" + telefono + '\'' +
               ", Estado=" + (estado ? "Activo" : "Inactivo") +
               '}';
    }
}
