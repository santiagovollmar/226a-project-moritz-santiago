package api.webContext.domain.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public class UserController {

	private UserServiceImpl userService;

	/**
	 *
	 */
	public UserController() {
	}

	/**
	 * @param userService
	 */
	@Autowired
	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * This method returns the requested user
	 *
	 * @param id Id of the requested user
	 * @return ResponseEntity with the user that was requested
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User user = userService.findById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * This method returns all users
	 *
	 * @return ResponseEntity with all existing users
	 */
	@GetMapping({ "", "/" })
	public @ResponseBody ResponseEntity<List<User>> getAll() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * This method creates an user
	 *
	 * @return ResponseEntity with the user that was created
	 */
	@PostMapping({ "", "/" })
	public ResponseEntity<User> create(@Valid @RequestBody User user) {
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	/**
	 * This method updates the requested user
	 *
	 * @param id   Id of the user that should get updated
	 * @param user user entity to which the requested user should get updated to
	 * @return ResponseEntity with the updated user
	 */
	@PutMapping("/{id}")
	public ResponseEntity<User> updateById(@PathVariable Long id, @Valid @RequestBody User user) {
		userService.update(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * This method deletes the requested user
	 *
	 * @param id Id of the user that should be deleted
	 * @return ResponseEntity with the outcome of the deletion process
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}