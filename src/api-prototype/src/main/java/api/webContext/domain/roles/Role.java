package api.webContext.domain.roles;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import api.config.generic.ExtendedEntity;
import api.webContext.domain.authority.Authority;

/**
 * Entity Role. A user can hold multiple roles e.g "ROLE_ADMIN".
 *
 * @author Moritz Lauper
 */
@Entity
@Table(name = "role")
public class Role extends ExtendedEntity {

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_authority", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> authorities;

	/**
	 *
	 */
	public Role() {
		super();
	}

	/**
	 * @param name
	 * @param authorities
	 */
	public Role(String name, Set<Authority> authorities) {
		this.name = name;
		this.authorities = authorities;
	}

	/**
	 * @param id
	 * @param role
	 * @param authorities
	 */
	public Role(Long id, String name, Set<Authority> authorities) {
		super(id);
		this.name = name;
		this.authorities = authorities;
	}

	/**
	 * @return the role
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param role the role to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the authorities
	 */
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

}
