package example.company.tox.common;

public class NotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAvailableException(String message) {
		super(message);
	}

}
