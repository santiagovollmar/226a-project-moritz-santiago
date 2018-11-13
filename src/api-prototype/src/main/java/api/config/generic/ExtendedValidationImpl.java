package api.config.generic;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import ch.nyp.noa.config.PropertyReader;

@Component
public abstract class ExtendedValidationImpl implements ExtendedValidation {

	// TODO define error messages(e.g 4001, 4002)

	private static final Pattern DATE_REGEX = Pattern.compile("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$");

	protected PropertyReader propertyReader;

	public ExtendedValidationImpl() throws IOException {
		propertyReader = new PropertyReader("message.properties");
	}

	@Override
	public boolean evaluateIfEmptyOrWhitespace(Errors error, String field) {
		Object value = error.getFieldValue(field);
		if (value == null || !StringUtils.hasText(value.toString())) {
			error.rejectValue(field, "4001", propertyReader.getStringProperty("4001"));
			return true;
		}
		return false;
	}

	@Override
	public boolean evaluateIfEmpty(Errors error, String field) {
		Object value = error.getFieldValue(field);
		if (value == null) {
			error.rejectValue(field, "4002", propertyReader.getStringProperty("4001"));
			return true;
		}
		return false;
	}

	@Override
	public boolean evaluateIfDateValid(Errors error, String field) {
		String value = (error.getFieldValue(field)).toString();
		Matcher m = DATE_REGEX.matcher(value);
		if (!m.matches()) {
			error.rejectValue(field, "4003", propertyReader.getStringProperty("4003"));
			return true;
		}
		return false;
	}
}
