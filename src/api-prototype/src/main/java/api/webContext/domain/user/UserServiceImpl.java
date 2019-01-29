package api.webContext.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import api.config.generic.ExtendedServiceImpl;

/**
 * This interface holds all data access related methods targeted towards the
 * entity user.
 *
 * @author Moritz Lauper
 */

@Service
public class UserServiceImpl extends ExtendedServiceImpl<User> implements UserService {

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		super(repository);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User could not be found");
		}
		return new UserDetailsImpl(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByUsername(String name) {
		User user = ((UserRepository) repository).findByUsername(name);
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByUsername(String name) {
		((UserRepository) repository).deleteByUsername(name);
	}
}
