package progetto.exceptions;

public class DBSQLException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DBSQLException() {
		super();
	}

	public DBSQLException(String m) {
		super(m);
	}
}