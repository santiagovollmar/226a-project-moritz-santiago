package api.webContext.domain.roles;

import api.config.generic.ExtendedService;

/**
 * This interface holds all data access related methods targeted towards the
 * entity role.
 *
 * @author Moritz Lauper
 */
interface RoleService extends ExtendedService<Role> {
	
	/**
	 * This method finds an role with a given name
	 *
	 * @param  name Descriptive name of role
	 * @return      Returns requested role with given descriptive name of role
	 */
	Role findByName(String name);
	
	/**
	 * This method deletes the requested role with a given name
	 *
	 * @param name Descriptive name of role
	 */
	void deleteByName(String name);
}
