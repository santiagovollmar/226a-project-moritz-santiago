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

	/**
	 * This method finds a User with a given name
	 *
	 * @param name Descriptive name of User
	 * @return Returns requested User with given descriptive name of User
	 */
	User findByUsername(String name);

	/**
	 * This method deletes the requested User with a given name
	 *
	 * @param name Descriptive name of User
	 */
	void deleteByUsername(String name);
	
}
