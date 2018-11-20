package webContext.exceptionHandling;

/**
 * This class gives closer information of a validation violation.
 *
 * @author Yves Kaufmann
 */
public class ValidationError {
	
	private String status;
	
	private String field;
	
	private String message;
	
	/**
	 * @param status
	 * @param field
	 * @param message
	 */
	public ValidationError(String status, String field, String message) {
		super();
		this.status = status;
		this.field = field;
		this.message = message;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
