package api.config.generic;

import java.util.List;

/**
 * This interface covers the basic CRUD related methods
 *
 * @author Moritz Lauper
 */
public interface ExtendedService<E extends ExtendedEntity> {

	/**
	 * This method saves the given entity
	 *
	 * @param entity The entity to be saved
	 */
	void save(E entity);

	/**
	 * This method updates the given entity
	 *
	 * @param entity The entity to be updated
	 */
	void update(E entity);

	/**
	 * This method deletes the given entity
	 *
	 * @param entity The entity to be deleted
	 */
	void delete(E entity);

	/**
	 * This method deletes an entity with a given primary key
	 *
	 * @param id Primary key of entity
	 */
	void deleteById(Long id);

	/**
	 * This method finds all records of one entity
	 *
	 * @return Returns a list of all records of the given entity
	 */
	List<E> findAll();

	/**
	 * This method finds an entity with a given primary key
	 *
	 * @param id Primary key of entity
	 * @return Returns requested entity with given primary key id
	 */
	E findById(Long id);

	/**
	 * This method evaluates if the given id already exists
	 *
	 * @param id Primary key of entity
	 * @return Returns if the given id already exists
	 */
	boolean existsById(Long id);
}