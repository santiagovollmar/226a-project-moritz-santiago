package api.webContext.exceptionHandling;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import api.config.PropertyReader;
import api.config.UUIDGenerator;

/**
 * This class handles validation violations and general exceptions.
 *
 * @author Moritz Lauper
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MESSAGE_PROPERTIES_FILE = "message.properties";
	
	private static final String LOGGER_PROPERTIES_FILE = "logger.properties";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request
	) {
		BindingResult bindingResult = exc.getBindingResult();
		
		List<ValidationError> validationErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new ValidationError(fieldError.getCode(), fieldError.getField(),
						fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return new ResponseEntity<>(validationErrors, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * This method handles general exceptions.
	 *
	 * @param  exc Exception the exception
	 * @return     ResponseEntity with a GeneralError-Object (message, uuid and
	 *             timestamp)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GeneralError> handleException(Exception exc) {
		String message;
		String defaultUuid;
		
		try {
			// read default error message and uuid from property file
			PropertyReader messagePropReader = new PropertyReader(MESSAGE_PROPERTIES_FILE);
			message = messagePropReader.getStringProperty("message.default-error-message");
			defaultUuid = messagePropReader.getStringProperty("message.fail-uuid");
		} catch (IOException e) {
			// If property file could not read: set default message and uuid from code
			message = "Es ist ein Fehler aufgetreten beim Auslesen der Fehlermeldung aus dem Property-File.";
			defaultUuid = "UUID-read-failed";
		}
		
		// Generate uuid. If uuid generation failed, set default uuid from property file
		// or code (see above)
		String uuid;
		try {
			uuid = UUIDGenerator.generateUUID();
		} catch (Exception ex) {
			uuid = defaultUuid;
		}
		
		if (isDevelopmentMode()) {
			// If dev mode, returns the exception message and the first 5 lines of the stack
			// trace to the client.
			StackTraceElement[] stackTraceElements = exc.getStackTrace();
			String stackTranceAsString = "";
			for (int i = 0; i < 5; i++) {
				stackTranceAsString += stackTraceElements[i];
			}
			
			message = "Exc Message: " + exc.getMessage();
			message += ", Exc Stack Trace: " + stackTranceAsString;
		}
		
		// TODO Log-Eintrag mit IP, UUID, Stacktrace von Exception, etc.
		
		GeneralError error = new GeneralError(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, uuid,
				System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method returns true if development mode is on (value in
	 * logger.properties) or false, if development mode is off.
	 *
	 * @return boolean true if development mode is on, false if off or property-file
	 *         could not read.
	 */
	private boolean isDevelopmentMode() {
		boolean isDevelopmentMode;
		
		PropertyReader loggerPropReader;
		try {
			loggerPropReader = new PropertyReader(LOGGER_PROPERTIES_FILE);
			// int developmentMode =
			// loggerPropReader.getIntProperty("logger.development-mode");
			int developmentMode = 1;
			isDevelopmentMode = developmentMode != 0;
		} catch (IOException e) {
			isDevelopmentMode = false;
		}
		
		return isDevelopmentMode;
	}
}
