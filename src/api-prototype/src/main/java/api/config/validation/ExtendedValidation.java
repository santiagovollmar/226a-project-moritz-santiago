package ch.nyp.noa.config.validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;

import ch.nyp.noa.config.PropertyReader;

/**
 * This class covers the common methods for the validation.
 * 
 * @author Moritz Lauper
 */
public abstract class ExtendedValidation {
	
	private static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z-\\x7F-\\xFF]$");
	
	protected PropertyReader messageReader;
	
	public ExtendedValidation() throws IOException {
		messageReader = new PropertyReader("messages.properties");
	}
	
	// TODO should get handled by StringTrimmerEditor
	/*
	public boolean validateEmptyOrWhitespace(Errors errors, String field, String value) {
		if (!StringUtils.hasText(value.toString())) {
			errors.rejectValue(field,
					messageReader.getStringProperty("message.general"));
			return true;
		}
		return false;
	}
	
	public boolean validateEmpty(Errors errors, String field, String value) {
		if (StringUtils.isEmpty(value)) {
			errors.rejectValue(field,
					messageReader.getStringProperty("message.connection"));
			return true;
		}
		return false;
	}
	*/
	
	/**
	 * This method validates if the given value matches the given regex.
	 * 
	 * @param  errors  Stores and exposes information about data-binding and
	 *                     validation errors for a specific object.
	 * @param  field   Field which get validated.
	 * @param  value   Value from the field which gets validated.
	 * @param  pattern Regex to math the value.
	 * @param  message Errormessage if the regex don't match.
	 * @return         boolean If validation is successful or not.
	 */
	public boolean validateRegexMatch(Errors errors, String field, String value, Pattern pattern, String message) {
		
		Matcher m = pattern.matcher(value);
		if (!m.matches()) {
			errors.rejectValue(field,
					messageReader.getStringProperty(message));
			return false;
		}
		return true;
	}
	
	/**
	 * This method validates if a String has the correct length.
	 * 
	 * @param  value String wich gets validated.
	 * @param  min   Minimal length of String.
	 * @param  max   Maximal length of String.
	 * @return       boolean If validation is successful or not.
	 */
	public boolean
			validateStringLength(Errors errors, String field, String value, Integer min, Integer max, String message) {
		
		if (value.length() >= min && value.length() <= max) {
			return true;
		}
		
		errors.rejectValue(field,
				messageReader.getStringProperty(message));
		return false;
	}
	
	/**
	 * This method validates an Aplhanumeric-field.
	 *
	 * @param  error Stores and exposes information about data-binding and
	 *                   validation errors for a specific object.
	 * @param  field Field which get validated.
	 * @param  value Value from the field wich gets validated.
	 * @return       boolean If validation is successful or not.
	 */
	public boolean validateAlphanumeric(Errors errors, String field, String value) {
		
		if (validateRegexMatch(errors, field, value, NAME_REGEX, "alphanummeric-syntax")) {
			return true;
		}
		
		return false;
	}
	
}
