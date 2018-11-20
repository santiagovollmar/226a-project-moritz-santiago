package ch.nyp.noa.config.validation;

import org.springframework.validation.Errors;

import ch.nyp.noa.config.validation.routing.ValidationMethod;

public class NoaValidationUtils { // TODO document this
	
	public static <TargetType> boolean call(
			ValidationMethod<TargetType> method, Iterable<TargetType> targets,
			String subPath, Errors errors
	) {
		boolean validationResult = false;
		
		StringBuilder builder = new StringBuilder(subPath.length() + 5);
		
		int index = 0;
		for (var target : targets) {
			// build and push nested path
			builder.append(subPath);
			builder.append('[');
			builder.append(index++);
			builder.append(']');
			errors.pushNestedPath(builder.toString());
			
			// validate
			validationResult &= method.validate(target, errors);
			
			// pop nested path and reset builder
			errors.popNestedPath();
			builder.setLength(0); // "clear" buffer
		}
		
		return validationResult;
	}
	
	public static <TargetType> boolean call(
			ValidationMethod<TargetType> method, TargetType target, String subPath,
			Errors errors
	) {
		boolean validationResult = false;
		
		errors.pushNestedPath(subPath);
		validationResult = method.validate(target, errors);
		errors.popNestedPath();
		
		return validationResult;
	}
}
