package webContext.exceptionHandling;

/**
 * This class gives closer information of a validation violation.
 *
 * @author Moritz Lauper
 */
public class GeneralError {
	
	private int status;
	
	private String message;
	
	private String uuid;
	
	private long timestamp;
	
	/**
	 *
	 */
	public GeneralError() {
		super();
	}
	
	/**
	 * @param status
	 * @param message
	 * @param uuid
	 * @param timestamp
	 */
	public GeneralError(int status, String message, String uuid, long timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.uuid = uuid;
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
