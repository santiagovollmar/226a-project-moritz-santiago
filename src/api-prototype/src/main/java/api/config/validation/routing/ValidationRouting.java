package ch.nyp.noa.config.validation.routing;

/**
 * This class represents a routing between a class and an associated validation
 * method.
 *
 * @author Santiago Gabriel Vollmar
 * @param  <TargetType>
 */
public class ValidationRouting<TargetType> {
	
	private final Class<TargetType> routedClass;
	
	private final ValidationMethod<TargetType> method;
	
	/**
	 * @param clazz
	 * @param method
	 */
	public ValidationRouting(Class<TargetType> routedClass, ValidationMethod<TargetType> method) {
		super();
		this.routedClass = routedClass;
		this.method = method;
	}
	
	/**
	 * @return the routedClass
	 */
	public Class<TargetType> getRoutedClass() {
		return routedClass;
	}
	
	/**
	 * @return the method
	 */
	public ValidationMethod<TargetType> getMethod() {
		return method;
	}
}
