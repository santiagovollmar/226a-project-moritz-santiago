package api.config.validation;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import api.config.validation.annotation.Validation;
import api.config.validation.util.ValidationNotFoundException;

@Component
@Scope("singleton")
public class ValidationRegistry {
	
	/**
	 * Stores the associations between validation classes and the entity classes
	 * they are able to validate <br>
	 * key: entity class <br>
	 * value: validation class <br>
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
	
	/**
	 * Stores pre-built {@link ValidationClassSkeleton}s
	 * key: entityClass <br>
	 * value: pre-built skeleton <br>
	 */
	private Map<Class<?>, ValidationClassSkeleton> classSkeletons;
	
	private ApplicationContext applicationContext;
	
	private Logger logger;
	
	/**
	 * 
	 */
	@Autowired
	public ValidationRegistry(ApplicationContext applicationContext, Logger logger) {
		super();
		this.registry = new HashMap<>();
		this.classSkeletons = new HashMap<>();
		this.applicationContext = applicationContext;
	}
	
	public void register(Class<?> validationClass, Validation annotation) {
		Class<?> entityClass = annotation.value();
		
		try {
			classSkeletons.put(entityClass, new ValidationClassSkeleton(validationClass, annotation, logger));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return;
		}
		
		registry.put(entityClass, validationClass);
	}
	
	public ValidationClassSkeleton findValidationClassSkeleton(Class<?> entityClass) {
		return findMapEntryByClassKey(classSkeletons, entityClass);
	}
	
	public Class<?> findValidationClassByEntity(Class<?> entityClass) {
		return findMapEntryByClassKey(registry, entityClass);
	}
	
	public Object findValidationInstanceByEntity(Class<?> entityClass) throws ValidationNotFoundException {
		Class<?> validationClass = findValidationClassByEntity(entityClass);
		
		if (validationClass != null) {
			return buildValidationInstance(validationClass);
		} else {
			throw new ValidationNotFoundException(
					String.format("Could not find Validation class for entity class '%s'", entityClass.getName()));
		}
	}
	
	public Object buildValidationInstance(Class<?> validationClass) throws ValidationNotFoundException {
		try {
			return applicationContext.getBean(validationClass);
		} catch (Exception e) {
			throw new ValidationNotFoundException(
					String.format("Could not instantiate Validation bean for class '%s'", validationClass.getName()), e);
		}
	}
	
	private <T> T findMapEntryByClassKey(Map<Class<?>, T> map, Class<?> key) {
		if (key == null) {
			try {
				return map.get(null);
			} catch (NullPointerException ex) { // map doesn't premit use of null keys
				return null;
			}
		}
		
		T value = map.get(key);
		if (value != null) { // value was found
			return value;
		}
		
		// recursive call with superclass
		// --> searches whole inheritance tree (stop when reaching Object)
		var superClass = key.getSuperclass();
		return superClass != Object.class ? findMapEntryByClassKey(map, superClass) : null;
	}
}
