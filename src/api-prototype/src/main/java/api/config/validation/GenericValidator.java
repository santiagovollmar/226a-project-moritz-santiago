package ch.nyp.noa.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.nyp.noa.config.validation.util.FieldGetter;
import ch.nyp.noa.config.validation.util.MultiFieldValidation;
import ch.nyp.noa.config.validation.util.Pair;
import ch.nyp.noa.config.validation.util.SingleFieldValidation;
import ch.nyp.noa.config.validation.util.ValidationNotFoundException;

@Component
public class GenericValidator implements Validator {
	
	private ValidationRegistry registry;
	
	private Logger logger;
	
	/**
	 * @param registry
	 * @param logger
	 */
	@Autowired
	public GenericValidator(ValidationRegistry registry, Logger logger) {
		super();
		this.registry = registry;
		this.logger = logger;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true; // TODO maybe check if any validation class for this target class is present in
						// the registry
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationClassSkeleton skeleton = registry.findValidationClassSkeleton(target.getClass());
		if (skeleton != null) {
			validate0(errors, target, skeleton);
		} else {
			logger.warn(
					String.format("Could not find ValidationClassSkeleton for target of type '%s'", target.getClass().getName()));
		}
	}
	
	private void validate0(Errors errors, Object target, ValidationClassSkeleton skeleton) {
		// check target type (must be same as or child-class of skeleton's entity class)
		if (!skeleton.getEntityClass().isAssignableFrom(target.getClass())) { return; }
		
		// obtain validation class instance
		Object validationInstance;
		try {
			validationInstance = registry.buildValidationInstance(skeleton.getValidationClass());
		} catch (ValidationNotFoundException ex) {
			logger.warn(String.format("Could not obtain instance of validation class '%s' when validating target of type '%s'",
					skeleton.getValidationClass().getName(), target.getClass().getName()), ex);
			return;
		}
		
		// validate single fields
		for (Pair<FieldGetter, SingleFieldValidation> validationPair : skeleton.getSingleFieldValidations()) {
			Object fieldValue = validationPair.left.get(target);
			
			if (fieldValue != null) { // don't validate null values
				String fieldName = validationPair.left.getFieldName();
				validationPair.right.call(validationInstance, errors, fieldName, fieldValue);
			}
		}
		
		// validate multiple fields
		for (Pair<List<FieldGetter>, MultiFieldValidation> validationPair : skeleton.getMultiFieldValidations()) {
			int len = validationPair.left.size();
			var fieldNames = new ArrayList<String>(len);
			var fieldValues = new ArrayList<Object>(len);
			
			for (FieldGetter fieldGetter : validationPair.left) {
				fieldNames.add(fieldGetter.getFieldName());
				fieldValues.add(fieldGetter.get(target));
			}
			
			validationPair.right.call(validationInstance, errors, fieldNames, fieldValues);
		}
		
		// validate delegations
		for (FieldGetter fieldGetter : skeleton.getFieldDelegations()) {
			Object value = fieldGetter.get(target);
			if (value != null) {
				if (value instanceof Iterable) {
					var iterator = ((Iterable<?>) value).iterator();
					
					var builder = new StringBuilder(fieldGetter.getFieldName().length() + 5);
					for (int i = 0; iterator.hasNext(); i++) {
						builder.append(fieldGetter.getFieldName());
						builder.append('[');
						builder.append(i);
						builder.append(']');
						
						delegate(errors, iterator.next(), null, builder.toString()); // recursive call for delegation
						builder.setLength(0); // keep old buffer but reset builder
					}
				} else {
					delegate(errors, value, fieldGetter.getType(), fieldGetter.getFieldName()); // recursive call for delegation
				}
			}
		}
		
		return;
	}
	
	private void delegate(Errors errors, Object value, Class<?> entityClass, String subPath) {
		// return if value is null
		if (value == null) { return; }
		
		// set entityClass to class of value if nothing was given
		if (entityClass == null) {
			entityClass = value.getClass();
		}
		
		ValidationClassSkeleton delegateSkeleton = registry.findValidationClassSkeleton(entityClass);
		if (delegateSkeleton != null) {
			errors.pushNestedPath(subPath);
			validate0(errors, value, delegateSkeleton);
			errors.popNestedPath();
		} else {
			logger.warn(String.format("Could not find ValidationClassSkeleton for target of type '%s'", entityClass.getName()));
		}
	}
}
