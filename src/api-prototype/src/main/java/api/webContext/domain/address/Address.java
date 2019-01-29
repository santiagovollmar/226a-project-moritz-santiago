package api.webContext.domain.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import api.config.generic.ExtendedEntity;

/**
 * This class is the entity address. An user or a location can hold multiple
 * addresses.
 *
 * @author Moritz Lauper
 */

@Entity
@Table(name = "address")
public class Address extends ExtendedEntity {

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "streetnumber")
	private String streetnumber;

	@Column(name = "zip")
	private String zip;

	/**
	 *
	 */
	public Address() {
		super();
	}

	public Address(String city, String street, String streetnumber, String zip) {
		super();
		this.city = city;
		this.street = street;
		this.streetnumber = streetnumber;
		this.zip = zip;
	}

	public Address(Long id, String city, String street, String streetnumber, String zip) {
		super(id);
		this.city = city;
		this.street = street;
		this.streetnumber = streetnumber;
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetnumber() {
		return streetnumber;
	}

	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
