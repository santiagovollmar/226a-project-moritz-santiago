package api.webContext.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
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
}

