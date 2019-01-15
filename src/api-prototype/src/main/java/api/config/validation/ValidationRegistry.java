package ch.nyp.noa.config.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ValidationRegistry {
	
	/**
	 * Stores registered Validation-classes as values using the class which they can
	 * validate as key
	 */
	private Map<Class<?>, Class<?>> registry; // TODO possible heap pollution (pre-1.8: PermGen leak) when code hot-swapping
												// is enabled, because old classes will never get purged (newly loaded classes
												// have different hashcodes because they were loaded by a different class
												// loader) --> Use WeakHashMap instead of regular HashMap & wrap value inside a
												// WeakReference (if possible use same purge-queue for keys and values)
												// --> also make sure that newly loaded classes (hot-swapping) will even get
												// registered by ValidationClassRegistrar (or does it really just run once at
												// the application start). If class registration through the runner doesn't work
												// we would maybe have to use a java agent for introspection (overkill) or
												// somehow hook into the class loading process of the class loader used by
												// spring (using this approach one would also need to make sure that annotated
												// classes would still even get loaded so we would still need some kind of
												// classpath search which would also be invoked on hot code-swaps (in case new
												// Validation-classes get added while the application is running))
	
	private ApplicationContext applicationContext;
	
	/**
	 * 
	 */
	@Autowired
	public ValidationRegistry(ApplicationContext applicationContext) {
		super();
		this.registry = new HashMap<>();
		this.applicationContext = applicationContext;
	}
	
	public void register(Class<?> entityClass, Class<?> validationClass) {
		registry.put(entityClass, validationClass);
	}
	
	public Class<?> getValidationClass(Class<?> entityClass) {
		return registry.get(entityClass);
	}
	
	public Object getValidationEntity(Class<?> entityClass) throws ValidationNotFoundException {
		try {
			return applicationContext.getBean(getValidationClass(entityClass));
		} catch (Exception e) {
			throw new ValidationNotFoundException(
					String.format("Could not find Validation class for entity '%s'", entityClass.getName()), e);
		}
	}
}
