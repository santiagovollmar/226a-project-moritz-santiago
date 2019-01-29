package api.webContext.domain.user;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import api.config.generic.ExtendedEntity;
import api.config.mapping.Mappable;

/**
 * This class is the Entity User. A User can hold multiple roles with its own
 * authorities.
 *
 * @author Moritz Lauper
 */

@Entity
@Table(name = "address")
public class User extends ExtendedEntity {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private Date birthdate;

	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, Date birthdate) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}

	public User(Long id, String username, String password, String firstName, String lastName, Date birthdate) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

}