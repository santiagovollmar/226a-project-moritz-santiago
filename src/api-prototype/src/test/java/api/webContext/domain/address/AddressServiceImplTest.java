package api.webContext.domain.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.from;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import api.config.generic.ExtendedServiceImplTest;

/**
 * @author Moritz Lauper
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest extends ExtendedServiceImplTest<AddressServiceImpl, Address, AddressRepository> {
	
	@InjectMocks
	private AddressServiceImpl addressServiceImpl;

	@Mock
	private AddressRepository addressRepository;

	Address address1;
	
	Address address2;
	
	@Before
	public void setup() {

		address1 = new Address(1L, "Zürich", "Herostrasse", "12", "8034");
		address2 = new Address(2L, null, null, null, null);
		
		super.setup(addressServiceImpl, addressRepository, address1, address2);
	}
	
	@Test
	public void getStreet_givenStreetDoesExist_returnsZuerich() {
		assertThat(address1.getStreet()).isEqualToIgnoringCase("HEROSTRASSE");
	}

	@Test
	public void getStreet_givenStreetDoesNotExist_returnsNull() {
		assertThat(address2.getStreet()).isNull();
	}

	@Test
	public void getStreetnumber_givenStreetnumberDoesExist_returnsZuerich() {
		assertThat(address1).returns("12", from(Address::getStreetnumber));
	}

	@Test
	public void getStreetnumber_givenStreetnumberDoesNotExist_returnsNull() {
		assertThat(address2.getStreetnumber()).isNull();
	}
	
}
