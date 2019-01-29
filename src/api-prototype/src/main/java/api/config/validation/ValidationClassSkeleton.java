package ch.nyp.noa.config.validation;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;

import ch.nyp.noa.config.validation.annotation.ValidateField;
import ch.nyp.noa.config.validation.annotation.ValidateFields;
import ch.nyp.noa.config.validation.annotation.Validation;
import ch.nyp.noa.config.validation.util.FieldGetter;
import ch.nyp.noa.config.validation.util.MultiFieldValidation;
import ch.nyp.noa.config.validation.util.Pair;
import ch.nyp.noa.config.validation.util.SingleFieldValidation;

public class ValidationClassSkeleton {
	
	private Class<?> validationClass;
	
	private Class<?> entityClass;
	
	private List<Pair<FieldGetter, SingleFieldValidation>> singleFieldValidations;
	
	private List<Pair<List<FieldGetter>, MultiFieldValidation>> multiFieldValidations;
	
	private List<FieldGetter> fieldDelegations;
	
	public ValidationClassSkeleton(Class<?> validationClass, Validation annotation, Logger logger)
			throws IllegalArgumentException, IntrospectionException {
		// initialize members
		this.validationClass = validationClass;
		this.entityClass = annotation.value();
		this.singleFieldValidations = new ArrayList<>();
		this.multiFieldValidations = new ArrayList<>();
		this.fieldDelegations = new ArrayList<>();
		
		// analyze entity class
		var entityClassGetters = new HashMap<String, FieldGetter>();
		for (var propertyDescriptor : Introspector.getBeanInfo(entityClass, Object.class).getPropertyDescriptors()) {
			try {
				var fieldGetter = new FieldGetter(propertyDescriptor);
				entityClassGetters.put(fieldGetter.getFieldName(), fieldGetter);
			} catch (IllegalArgumentException ex) {
				continue; // skip unreadable field
			}
		}
		
		// fetch validation methods
		outer: for (var method : validationClass.getDeclaredMethods()) {
			var singleFieldAnnotation = method.getAnnotation(ValidateField.class);
			var multiFieldAnnotation = method.getAnnotation(ValidateFields.class);
			
			// throw exception if both annotations are present on the current method
			if (singleFieldAnnotation != null && multiFieldAnnotation != null) { throw new IllegalArgumentException(String.format(
					"Validation method '%s' declared in class '%s' is both annotated with @ValidateFields and @ValidateField (must only be annotated with one of them)",
					method.getName(), validationClass.getName())); }
			
			if (singleFieldAnnotation != null) {
				String fieldName = singleFieldAnnotation.value();
				var fieldGetter = entityClassGetters.get(fieldName);
				var fieldValidation = new SingleFieldValidation(method); // let possible IllegalArgumentException just bubble up
				
				if (fieldGetter != null) {
					this.singleFieldValidations.add(new Pair<>(fieldGetter, fieldValidation));
				} else {
					logger.warn(String.format(
							"@ValidateFields of validation method '%s' declared in class '%s' specifies unknown field '%s' for entity '%s'",
							method.getName(), validationClass.getName(), fieldName, entityClass.getName()));
				}
			} else if (multiFieldAnnotation != null) {
				String[] fieldNames = multiFieldAnnotation.value();
				
				// create MultiFieldValidation object
				var multiFieldValidation = new MultiFieldValidation(method, fieldNames.length); // let possible
																								// IllegalArgumentException just
																								// bubble up
				
				// create field getter list
				var fieldGetters = new ArrayList<FieldGetter>();
				for (String fieldName : fieldNames) {
					var fieldGetter = entityClassGetters.get(fieldName);
					if (fieldGetter != null) {
						fieldGetters.add(fieldGetter);
					} else { // an unknown field was specified
						logger.warn(String.format(
								"@ValidateFields of validation method '%s' declared in class '%s' specifies unknown field '%s' for entity '%s'",
								method.getName(), validationClass.getName(), fieldName, entityClass.getName()));
						continue outer; // skip this method
					}
				}
				
				// put into multivalidations list
				this.multiFieldValidations.add(new Pair<>(fieldGetters, multiFieldValidation));
			}
		}
		
		// fetch field delegations
		String[] delegations = annotation.delegations();
		for (var delegation : delegations) {
			var fieldGetter = entityClassGetters.get(delegation);
			if (fieldGetter != null) {
				this.fieldDelegations.add(fieldGetter);
			} else {
				logger.warn(String.format(
						"@Validation of class '%s' specifies unknown field '%s' as field delegation for entity '%s'",
						validationClass.getName(), delegation, entityClass.getName()));
			}
		}
	}
	
	/**
	 * @return the validationClass
	 */
	public Class<?> getValidationClass() {
		return validationClass;
	}
	
	/**
	 * @param validationClass the validationClass to set
	 */
	public void setValidationClass(Class<?> validationClass) {
		this.validationClass = validationClass;
	}
	
	/**
	 * @return the entityClass
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	/**
	 * @return the singleFieldValidations
	 */
	public List<Pair<FieldGetter, SingleFieldValidation>> getSingleFieldValidations() {
		return singleFieldValidations;
	}
	
	/**
	 * @param singleFieldValidations the singleFieldValidations to set
	 */
	public void setSingleFieldValidations(List<Pair<FieldGetter, SingleFieldValidation>> singleFieldValidations) {
		this.singleFieldValidations = singleFieldValidations;
	}
	
	/**
	 * @return the multiFieldValidations
	 */
	public List<Pair<List<FieldGetter>, MultiFieldValidation>> getMultiFieldValidations() {
		return multiFieldValidations;
	}
	
	/**
	 * @param multiFieldValidations the multiFieldValidations to set
	 */
	public void setMultiFieldValidations(List<Pair<List<FieldGetter>, MultiFieldValidation>> multiFieldValidations) {
		this.multiFieldValidations = multiFieldValidations;
	}
	
	/**
	 * @return the fieldDelegations
	 */
	public List<FieldGetter> getFieldDelegations() {
		return fieldDelegations;
	}
	
	/**
	 * @param fieldDelegations the fieldDelegations to set
	 */
	public void setFieldDelegations(List<FieldGetter> fieldDelegations) {
		this.fieldDelegations = fieldDelegations;
	}
	
}
