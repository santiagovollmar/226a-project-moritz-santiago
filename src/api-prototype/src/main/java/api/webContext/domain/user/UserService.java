package api.webContext.domain.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import api.config.generic.ExtendedService;

public interface UserService extends ExtendedService<User>, UserDetailsService {
	/**
	 * This method finds a User with a given name
	 *
	 * @param  name Descriptive name of User
	 * @return      Returns requested User with given descriptive name of User
	 */
	User findByUsername(String name);
	
	/**
	 * This method deletes the requested User with a given name
	 *
	 * @param name Descriptive name of User
	 */
	void deleteByUsername(String name);
}
