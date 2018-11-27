package webContext.domain.email;

import api.config.generic.ExtendedEntity;
import api.config.mapping.Mappable;

/**
 * This class is the entity email. A user can hold multiple emails.
 *
 * @author Moritz Lauper
 */
@Mappable
public class Email extends ExtendedEntity {
	
	private String name;
	
	/**
	 *
	 */
	public Email() {
		super();
	}
	
	public Email(String name) {
		this.name = name;
	}
	
	/**
	 * @param name
	 */
	public Email(Long id, String name) {
		super(id);
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
