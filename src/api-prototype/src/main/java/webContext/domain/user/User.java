import java.sql.Date;

import api.config.generic.ExtendedEntity;
import api.config.mapping.Mappable;

/**
 * This class is the Entity User. A User can hold multiple roles with its own
 * authorities.
 *
 * @author Moritz Lauper
 */

@Mappable
public class User extends ExtendedEntity {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private Date birthdate;

	private Email email;

	private Contract contract;

	private Set<UserPhonenumber> userPhonenumbers;

	private Set<GroupUser> groupUsers;

	private Address address;

	private Set<Role> roles;

	/**
	 *
	 */
	public User() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param email
	 * @param contract
	 * @param userPhonenumbers
	 * @param groupUsers
	 * @param address
	 * @param roles
	 */
	public User(
			String username, String password, String firstName, String lastName, Date birthdate, Email email,
			Contract contract, Set<UserPhonenumber> userPhonenumbers, Set<GroupUser> groupUsers, Address address,
			Set<Role> roles, Date accountExpirationDate
	) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.contract = contract;
		this.userPhonenumbers = userPhonenumbers;
		this.groupUsers = groupUsers;
		this.address = address;
		this.roles = roles;

	}

	/**
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param email
	 * @param contract
	 * @param userPhonenumbers
	 * @param groupUsers
	 * @param address
	 * @param roles
	 * @param accountExpirationDate
	 */
	public User(
			Long id, String username, String password, String firstName, String lastName, Date birthdate,
			Email email, Contract contract, Set<UserPhonenumber> userPhonenumbers, Set<GroupUser> groupUsers,
			Address address, Set<Role> roles
	) {
		super(id);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
		this.contract = contract;
		this.userPhonenumbers = userPhonenumbers;
		this.groupUsers = groupUsers;
		this.address = address;
		this.roles = roles;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(Email email) {
		this.email = email;
	}

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * @return the userPhonenumbers
	 */
	public Set<UserPhonenumber> getUserPhonenumbers() {
		return userPhonenumbers;
	}

	/**
	 * @param userPhonenumbers the userPhonenumbers to set
	 */
	public void setUserPhonenumbers(Set<UserPhonenumber> userPhonenumbers) {
		this.userPhonenumbers = userPhonenumbers;
	}

	/**
	 * @return the groupUsers
	 */
	public Set<GroupUser> getGroupUsers() {
		return groupUsers;
	}

	/**
	 * @param groupUsers the groupUsers to set
	 */
	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the accountExpirationDate
	 */
	public Date getAccountExpirationDate() {
		return accountExpirationDate;
	}

}