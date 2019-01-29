package api.webContext.domain.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import api.config.generic.ExtendedEntity;

/**
 * This class is the entity authority. A role can hold multiple authorities e.g
 * "WRITE_PRIVILEDGE" or "READ_PRIVILEDGE".
 *
 * @author Moritz Lauper
 */
@Entity
@Table(name = "authority")
public class Authority extends ExtendedEntity {
	
	@Column(name = "name")
	private String name;
	
	/**
	 *
	 */
	public Authority() {
		super();
	}
	
	/**
	 * @param id
	 */
	public Authority(Long id) {
		super(id);
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public Authority(Long id, String authority) {
		super(id);
		name = authority;
	}
	
	/**
	 * @param name
	 */
	public Authority(String name) {
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
