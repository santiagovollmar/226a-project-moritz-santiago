package ch.nyp.noa.config.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.nyp.noa.config.validation.routing.RouteValidator;

/**
 * @deprecated Use {@link RouteValidator} instead
 *             <br>
 *             <br>
 *             This class combines multiple validators into a single composite
 *             validator.
 *             This class is a convenience class, allowing for the use of
 *             multiple
 *             validators with possibly different target types without the use
 *             of different
 *             initBinders. When prompted to validate a target, this class will
 *             call the
 *             {@link Validator#validate(Object, Errors)} function of every
 *             supplied
 *             validator which supports the target class.
 *             {@link Validator#supports(Class)}
 *             will return true, if at least one of the supplied validators,
 *             supports the
 *             given class.
 * @author     Santiago Gabriel Vollmar
 */
@Deprecated
public class CompositeValidator implements Validator {
	
	private final Validator[] validators;
	
	/**
	 * @param validators
	 */
	public CompositeValidator(Validator ...validators) {
		this.validators = validators;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		for (Validator validator : validators) {
			if (validator.supports(clazz)) { return true; }
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Class<?> clazz = target.getClass();
		
		for (Validator validator : validators) {
			if (validator.supports(clazz)) {
				validator.validate(target, errors);
			}
		}
	}
	
}
