package ch.nyp.noa.config.validation;

import org.springframework.validation.Errors;

public interface ExtendedValidation {
	
	boolean evaluateIfEmptyOrWhitespace(Errors errors, String field, String value);
	
	boolean evaluateIfEmpty(Errors errors, String field, String value);
	
}
