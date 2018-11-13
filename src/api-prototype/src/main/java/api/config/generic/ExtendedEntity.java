package api.config.generic;

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class that covers the common attributes of all entities
 *
 * @author Moritz Lauper
 */
@MappedSuperclass
public abstract class ExtendedEntity {

	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 *
	 */
	public ExtendedEntity() {
		super();
	}

	/**
	 * @param id
	 */
	public ExtendedEntity(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}