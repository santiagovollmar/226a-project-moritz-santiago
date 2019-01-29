package api.config.validation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldGetter {
	
	private String fieldName;
	
	private Method getter;
	
	private Class<?> type;
	
	/**
	 * @param  propertyDescriptor
	 * @throws IllegalArgumentException
	 */
	public FieldGetter(PropertyDescriptor propertyDescriptor) throws IllegalArgumentException {
		super();
		this.type = propertyDescriptor.getPropertyType();
		this.getter = propertyDescriptor.getReadMethod();
		this.fieldName = propertyDescriptor.getName();
		
		// check constraints
		if (type == null || getter == null) { throw new IllegalArgumentException(); }
		
		getter.setAccessible(true);
	}
	
	public Object get(Object target) throws IllegalArgumentException {
		try {
			return getter.invoke(target);
		} catch (InvocationTargetException ex) {
			ex.printStackTrace(); // TODO maybe log this or present it differently
		} catch (IllegalAccessException e) {} // this should be impossible (see constructor)
		
		return null;
	}
	
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}
	
}
