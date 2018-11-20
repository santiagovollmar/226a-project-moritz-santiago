package ch.nyp.noa.config.validation.routing;

import org.springframework.validation.Errors;

/**
 * This interface is intended to be used to capture validation methods with a
 * specific target type as a lambda function.
 *
 * @see    RouteValidator
 * @see    ValidationRouting
 * @author Santiago Gabriel Vollmar
 * @param  <TargetType>
 */
public interface ValidationMethod<TargetType> {
	
	public boolean validate(TargetType target, Errors errors);
}
