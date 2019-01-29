package api.webContext.domain.user;

import org.springframework.stereotype.Repository;
import api.config.generic.ExtendedJpaRepository;

/**
 * This interface holds all data access related methods targeted towards the
 * entity user.
 *
 * @author Moritz Lauper
 */

@Repository
interface UserRepository extends ExtendedJpaRepository<User> {
	
}
