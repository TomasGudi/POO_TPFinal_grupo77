package ar.edu.unju.escmi.exceptions;

public class FechaNoDisponibleException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public FechaNoDisponibleException(String errorMessage) {
		super(errorMessage);
	}
}
