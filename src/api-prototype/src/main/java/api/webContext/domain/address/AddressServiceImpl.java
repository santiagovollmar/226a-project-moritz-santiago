package api.webContext.domain.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.config.generic.ExtendedServiceImpl;

/**
 * This interface holds all data access related methods targeted towards the
 * entity address.
 *
 * @author Moritz Lauper
 */

@Service
public class AddressServiceImpl extends ExtendedServiceImpl<Address> implements AddressService {
	
	@Autowired
	public AddressServiceImpl(AddressRepository repository) {
		super(repository);
	}
}
