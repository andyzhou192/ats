package net.common.exceptiom;

/**
 * 
 *
 *
 */
public class APIException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIException() {
		super();
	}

	public APIException(Throwable t) {
		super(t);
	}

	public APIException(String e) {
		super(e);
	}

}
