package api.config.generic;

import org.springframework.validation.Errors;

public interface ExtendedValidation {

	boolean evaluateIfEmptyOrWhitespace(Errors error, String field);

	boolean evaluateIfEmpty(Errors error, String field);

	boolean evaluateIfDateValid(Errors error, String field);
}
