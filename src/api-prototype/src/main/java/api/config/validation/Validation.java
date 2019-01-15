package ch.nyp.noa.config.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * This annotation is used for map a validation-class to its entity-class.
 * 
 * @author Moritz Lauper
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Validation {
	
	Class<?> value();
	
}
