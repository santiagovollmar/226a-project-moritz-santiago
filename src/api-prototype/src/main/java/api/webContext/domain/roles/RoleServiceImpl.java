package api.webContext.domain.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.config.generic.ExtendedServiceImpl;

/**
 * This class implements all data access related methods targeted towards the
 * entity role.
 *
 * @author Moritz Lauper
 */
@Service
class RoleServiceImpl extends ExtendedServiceImpl<Role> implements RoleService {
	
	/**
	 * @param repository
	 */
	@Autowired
	RoleServiceImpl(RoleRepository repository) {
		super(repository);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		Role role = ((RoleRepository) repository).findByName(name);
		return role;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByName(String name) {
		((RoleRepository) repository).deleteByName(name);
	}
}
