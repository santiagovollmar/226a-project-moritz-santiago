package api.webContext.domain.user;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import api.config.generic.ExtendedEntity;
import api.webContext.domain.address.Address;
import api.webContext.domain.roles.Role;

/**
 * This class is the Entity User. A User can hold multiple roles with its own
 * authorities.
 *
 * @author Moritz Lauper
 */

@Entity
@Table(name = "address")
public class User extends ExtendedEntity {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthdate")
	private LocalDate birthdate;
	
	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "users_role",
		joinColumns = @JoinColumn(name = "users_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;

	@Column(name = "account_expiration_date")
	private LocalDate accountExpirationDate;

	@Column(name = "credentials_expiration_date")
	private LocalDate credentialsExpirationDate;

	@Column(name = "locked")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean locked;

	@Column(name = "enabled")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean enabled;
	
	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, LocalDate birthdate,
			Address address, Set<Role> roles, LocalDate accountExpirationDate, LocalDate credentialsExpirationDate,
			Boolean locked, Boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.address = address;
		this.roles = roles;
		this.accountExpirationDate = accountExpirationDate;
		this.credentialsExpirationDate = credentialsExpirationDate;
		this.locked = locked;
		this.enabled = enabled;
	}

	public User(Long id, String username, String password, String firstName, String lastName, LocalDate birthdate,
			Address address, Set<Role> roles, LocalDate accountExpirationDate, LocalDate credentialsExpirationDate,
			Boolean locked, Boolean enabled) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.address = address;
		this.roles = roles;
		this.accountExpirationDate = accountExpirationDate;
		this.credentialsExpirationDate = credentialsExpirationDate;
		this.locked = locked;
		this.enabled = enabled;
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LocalDate getAccountExpirationDate() {
		return accountExpirationDate;
	}

	public void setAccountExpirationDate(LocalDate accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	public LocalDate getCredentialsExpirationDate() {
		return credentialsExpirationDate;
	}

	public void setCredentialsExpirationDate(LocalDate credentialsExpirationDate) {
		this.credentialsExpirationDate = credentialsExpirationDate;
	}

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}