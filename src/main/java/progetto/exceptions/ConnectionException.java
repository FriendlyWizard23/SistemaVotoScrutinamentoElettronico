package progetto.exceptions;

public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConnectionException() {
		super();
	}

	public ConnectionException(String m) {
		super(m);
	}
}