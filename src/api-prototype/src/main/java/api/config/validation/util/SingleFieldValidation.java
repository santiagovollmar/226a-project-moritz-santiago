package ch.nyp.noa.config.validation.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.validation.Errors;

public class SingleFieldValidation {
	
	private Method method;
	
	/**
	 * @param method
	 */
	public SingleFieldValidation(Method method) {
		super();
		this.method = method;
		
		// check constraints
		{
			Class<?> returnType = method.getReturnType();
			if (returnType != Void.TYPE) { throw new IllegalArgumentException(String.format(
					"Validation method '%s' declared in class '%s' has an illegal return type '%s'. (Must define 'void')",
					method.getName(), method.getDeclaringClass().getName(), returnType.getName())); }
			
			Class<?>[] paramTypes = method.getParameterTypes();
			try {
				if (!Errors.class.isAssignableFrom(paramTypes[0])
						|| paramTypes[1] != String.class) { throw new IllegalArgumentException(String.format(
								"Validation method '%s' declared in class '%s' defines illegal parameter types. (Must define exactly three parameters of types '%s', '%s' and '%s')",
								method.getName(), method.getDeclaringClass().getName(), Errors.class.getName(),
								String.class.getName(), Object.class.getName())); }
				
				if (paramTypes.length != 3) { throw new ArrayIndexOutOfBoundsException(); }
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new IllegalArgumentException(String.format(
						"Validation method '%s' declared in class '%s' has an illegal number of parameters '%d'. (Must define exactly three parameters of types '%s', '%s' and '%s')",
						method.getName(), method.getDeclaringClass().getName(), paramTypes.length, Errors.class.getName(),
						String.class.getName(), Object.class.getName()));
			}
		}
		
		method.setAccessible(true); // we a badass lol
	}
	
	public void call(Object target, Errors errors, String field, Object value) throws IllegalArgumentException {
		try {
			method.invoke(target, errors, field, value);
		} catch (InvocationTargetException ex) {
			ex.printStackTrace(); // TODO maybe log this or present it differently
		} catch (IllegalAccessException ex) {} // this should be impossible (see constructor)
	}
}
