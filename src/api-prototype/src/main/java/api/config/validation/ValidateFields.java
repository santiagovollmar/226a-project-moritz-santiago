package ch.nyp.noa.config.validation;
/**
 * This annotation is used to map a validation method to its fields.
 * 
 * @author Moritz Lauper
 */
public @interface ValidateFields {
	
	String[] value();
}
