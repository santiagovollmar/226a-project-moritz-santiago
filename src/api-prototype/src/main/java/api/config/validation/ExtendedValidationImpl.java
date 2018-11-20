package ch.nyp.noa.config.validation;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import ch.nyp.noa.config.PropertyReader;

@Component
public abstract class ExtendedValidationImpl implements ExtendedValidation {
	
	// TODO define error messages(e.g 4001, 4002)
	
	private static final Pattern DATE_REGEX = Pattern.compile("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$");
	
	protected PropertyReader messageReader;
	
	protected PropertyReader codeReader;
	
	public ExtendedValidationImpl() throws IOException {
		messageReader = new PropertyReader("errormessage.properties");
		codeReader = new PropertyReader("errorcode.properties");
	}
	
	@Override
	public boolean evaluateIfEmptyOrWhitespace(Errors errors, String field, String value) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			errors.rejectValue(field, codeReader.getStringProperty("code.general"),
					messageReader.getStringProperty("message.general"));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean evaluateIfEmpty(Errors errors, String field, String value) {
		if (value == null || StringUtils.isEmpty(value)) {
			errors.rejectValue(field, codeReader.getStringProperty("code.connection"),
					messageReader.getStringProperty("message.connection"));
			return true;
		}
		return false;
	}
	
}
