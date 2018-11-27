package ch.nyp.noa.config.validation.routing;

import java.util.HashMap;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * This class emulates a {@link Validator} by routing requests to validate an
 * object of a specific class to it's associated {@link ValidationMethod}. The
 * routings used by this class are provided by
 * {@link RouteValidatorRoutingProvider#provideRoutings()}, which is intended to
 * be implemented by a sub-classes. Note however, that
 * {@link ValidationRouting}s provided by
 * {@link RouteValidatorRoutingProvider#provideRoutings()} which have the same
 * <code>routingClass</code> will overwrite each other (with the last class in
 * the collection being dominant).
 *
 * @author Santiago Gabriel Vollmar
 */
public abstract class RouteValidator implements Validator { // TODO update documentation
	
	private HashMap<String, ValidationMethod<?>> routings;
	
	/**
	 *
	 */
	public RouteValidator(ValidationRouting<?> ...routings) {
		this.routings = new HashMap<>();
		
		for (ValidationRouting<?> routing : routings) {
			this.routings.put(routing.getRoutedClass().getName(), routing.getMethod());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean supports(Class<?> clazz) {
		return routings.containsKey(clazz.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void validate(Object target, Errors errors) {
		callValidationMethod(routings.get(target.getClass().getName()), target, errors);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	private final <T> void callValidationMethod(ValidationMethod<T> validationMethod, Object target, Errors errors) {
		if (validationMethod == null) { return; }
		validationMethod.validate((T) target, errors);
	}
}
