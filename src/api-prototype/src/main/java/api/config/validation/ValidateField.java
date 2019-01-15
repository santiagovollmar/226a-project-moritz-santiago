package ch.nyp.noa.config.validation;

/**
 * This annotation is used to map a validation method to its field.
 * 
 * @author Moritz Lauper
 */
public @interface ValidateField {
	
	String value();
	
}
