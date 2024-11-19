package ar.edu.unju.escmi.exceptions;

public class SalonNoDisponibleException extends Exception {

	private static final long serialVersionUID = 1876470878769819936L;

	public SalonNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

