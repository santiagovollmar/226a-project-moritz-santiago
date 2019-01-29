package api.webContext.domain.authority;

import org.springframework.stereotype.Repository;

import api.config.generic.ExtendedJpaRepository;

/**
 * This interface holds all data access related methods targeted towards the
 * entity authority.
 *
 * @author Moritz Lauper
 */
@Repository
interface AuthorityRepository extends ExtendedJpaRepository<Authority> {
	
	/**
	 * This method finds an authority with a given name.
	 *
	 * @param  name Descriptive name of Authority
	 * @return      Returns requested Authority with given descriptive name of
	 *              Authority
	 */
	Authority findByName(String name);
	
	/**
	 * This method deletes the requested authority with a given name.
	 *
	 * @param name Descriptive name of Authority
	 */
	void deleteByName(String name);
}
