package api.config.validation.util;

public class ValidationNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5121396255190668201L;
	
	/**
	 * 
	 */
	public ValidationNotFoundException() {
		super();
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ValidationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public ValidationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param message
	 */
	public ValidationNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public ValidationNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
