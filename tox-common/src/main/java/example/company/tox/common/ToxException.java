package example.company.tox.common;

public class ToxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ToxException(Exception e) {
		super(e);
	}

}
