package progetto.exceptions;

public class SessionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SessionNotFoundException() {
		super();
	}

	public SessionNotFoundException(String m) {
		super(m);
	}
}
