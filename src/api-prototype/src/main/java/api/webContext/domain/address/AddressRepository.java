package api.webContext.domain.address;


import org.springframework.stereotype.Repository;
import api.config.generic.ExtendedJpaRepository;

/**
 * This interface holds all data access related methods targeted towards the
 * entity address.
 *
 * @author Moritz Lauper
 */

@Repository
interface AddressRepository extends ExtendedJpaRepository<Address> {
	
}
