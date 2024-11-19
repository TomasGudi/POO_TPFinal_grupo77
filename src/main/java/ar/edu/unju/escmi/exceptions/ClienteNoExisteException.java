package ar.edu.unju.escmi.exceptions;

public class ClienteNoExisteException extends Exception {

	private static final long serialVersionUID = 8875187837859934670L;

	public ClienteNoExisteException(String mensaje) {
        super(mensaje);
    }
}

