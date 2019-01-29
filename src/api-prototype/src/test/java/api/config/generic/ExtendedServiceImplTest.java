package api.config.generic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExtendedServiceImplTest<S extends ExtendedServiceImpl<E>, E extends ExtendedEntity, R extends ExtendedJpaRepository<E>> {
	
	private List<E> entitiesToMock;

	private S service;
	
	private R repository;

	public void setup(S serviceImpl, R repository, E entityOneToMock, E entityTwoToMock) {
		this.service = serviceImpl;
		this.repository = repository;

		entitiesToMock = new ArrayList<>();
		entitiesToMock.add(entityOneToMock);
		entitiesToMock.add(entityTwoToMock);
	}

	@Test
	public void save_givenEntityDoesExist_savesEntity() {
		E objectToSave = entitiesToMock.get(0);
		when(repository.save(objectToSave)).thenReturn(objectToSave);

		service.save(objectToSave);
		verify(repository, times(1)).save(objectToSave);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void delete_givenEntityDoesExist_deletesEntity() {
		E objectToDelete = entitiesToMock.get(0);
		doNothing().when(repository).delete(objectToDelete);

		service.delete(objectToDelete);
		verify(repository, times(1)).delete(objectToDelete);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void deleteById_givenEntityDoesExist_deletesEntity() {
		E objectToDelete = entitiesToMock.get(0);
		long idToDelete = objectToDelete.getId();

		doNothing().when(repository).deleteById(idToDelete);

		service.deleteById(idToDelete);
		verify(repository, times(1)).deleteById(idToDelete);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void update_givenEntityDoesExist_updatesEntity() {
		E objectToUpdate = entitiesToMock.get(0);
		when(repository.save(objectToUpdate)).thenReturn(objectToUpdate);

		service.update(objectToUpdate);
		verify(repository, times(1)).save(objectToUpdate);
		verifyNoMoreInteractions(repository);
	}

	@Test
	public void findAll_returnsAllEntities() {
		when(repository.findAll()).thenReturn(entitiesToMock);
		assertEquals(entitiesToMock, service.findAll());
	}

	@Test
	public void findAll_returnsNoEntities() {
		List<E> emptyEntityList = new ArrayList<>();
		when(repository.findAll()).thenReturn(emptyEntityList);
		assertEquals(emptyEntityList, service.findAll());
	}

	@Test
	public void findById_givenIdDoesExist_returnsEntity() {
		E objectToFind = entitiesToMock.get(0);
		long idToFid = objectToFind.getId();

		Optional<E> repoReturnValue = Optional.of(objectToFind);
		when(repository.findById(idToFid)).thenReturn(repoReturnValue);

		assertEquals(objectToFind, service.findById(idToFid));
	}

	@Test(expected = NoSuchElementException.class)
	public void findById_givenIdDoesNotExist_throwsNoSuchElementException() {
		E objectToFind = entitiesToMock.get(0);
		long idToFid = objectToFind.getId();

		Optional<E> repoReturnValueEmpty = Optional.empty();
		when(repository.findById(idToFid)).thenReturn(repoReturnValueEmpty);

		service.findById(idToFid);
	}

	@Test
	public void existsById_givenIdDoesExist_returnsTrue() {
		E objectToFind = entitiesToMock.get(0);
		long idToFid = objectToFind.getId();

		when(repository.existsById(idToFid)).thenReturn(true);
		assertEquals(true, service.existsById(idToFid));
	}

	@Test
	public void existsById_givenIdDoesNotExist_returnsFalse() {
		E objectToFind = entitiesToMock.get(0);
		long idToFid = objectToFind.getId();

		when(repository.existsById(idToFid)).thenReturn(false);
		assertEquals(false, service.existsById(idToFid));
	}
}
