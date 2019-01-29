package api.config.validation.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.validation.Errors;

public class MultiFieldValidation {
	
	private Method method;
	
	private int numOfFields;
	
	/**
	 * @param method
	 * @param numOfFields
	 */
	public MultiFieldValidation(Method method, int numOfFields) {
		super();
		this.method = method;
		this.numOfFields = numOfFields;
		
		// check constraints
		{
			Class<?> returnType = method.getReturnType();
			if (returnType != Void.TYPE) { throw new IllegalArgumentException(String.format(
					"Validation method '%s' declared in class '%s' has an illegal return type '%s'. (Must define type 'Boolean' or 'boolean')",
					method.getName(), method.getDeclaringClass().getName(), returnType.getName())); }
			
			Class<?>[] paramTypes = method.getParameterTypes();
			
			// check number of types
			if (paramTypes.length != (numOfFields * 2 + 1)) { throw new IllegalArgumentException(String.format(
					"Validation method '%s' declared in class '%s' has an illegal number of parameters '%d'. (Must define exactly '%d' parameters)",
					method.getName(), method.getDeclaringClass().getName(), paramTypes.length, (numOfFields * 2 + 1))); }
			
			// check if first param has type Errors
			if (!Errors.class.isAssignableFrom(paramTypes[0])) { throw new IllegalArgumentException(String.format(
					"Validation method '%s' declared in class '%s' defines illegal type for first parameter. (Must be assignable to '%s')",
					method.getName(), method.getDeclaringClass().getName(), Errors.class.getName())); }
			
			// check if other params are always string-object pairs
			for (int i = 2; i < paramTypes.length; i += 2) {
				if (paramTypes[i - 1] != String.class) { throw new IllegalArgumentException(String.format(
						"Validation method '%s' declared in class '%s' must only define <String>-<?> pairs after its first parameter",
						method.getName(), method.getDeclaringClass().getName())); }
			}
		}
		
		method.setAccessible(true); // we a badass lol
	}
	
	public void call(Object target, Errors errors, List<String> fields, List<Object> values)
			throws IllegalArgumentException, IndexOutOfBoundsException {
		// check number of fields and values
		if (fields.size() != values.size() || fields.size() != numOfFields) { throw new IndexOutOfBoundsException(); }
		
		// fill all parameters into array
		var args = new Object[numOfFields * 2 + 1];
		args[0] = errors;
		
		for (int i = 2; i < args.length; i += 2) {
			int pairIndex = i / 2 - 1;
			
			args[i - 1] = fields.get(pairIndex);
			args[i] = values.get(pairIndex);
		}
		
		try {
			method.invoke(target, args);
		} catch (InvocationTargetException ex) {
			ex.printStackTrace(); // TODO maybe log this or present it differently
		} catch (IllegalAccessException ex) {} // this should be impossible (see constructor)
	}
}
