package api.webContext.domain.roles;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class holds all REST endpoints targeted towards the entity role.
 *
 * @author Moritz Lauper
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
	private RoleService roleService;
	
	/**
	 *
	 */
	public RoleController() {}
	
	/**
	 * @param roleService
	 * @param roleValidator
	 */
	@Autowired
	public RoleController( RoleService roleService) {
		this.roleService = roleService;
	}
	
	/**
	 * This method returns the requested role
	 *
	 * @param id Id of the requested role
	 * @return ResponseEntity with the role that was requested
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Role> getById(@PathVariable Long id) {
		Role role = roleService.findById(id);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	/**
	 * This method returns all rolees
	 *
	 * @return ResponseEntity with all existing rolees
	 */
	@GetMapping({ "", "/" })
	public @ResponseBody ResponseEntity<List<Role>> getAll() {
		List<Role> roles = roleService.findAll();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	/**
	 * This method creates an role
	 *
	 * @return ResponseEntity with the role that was created
	 */
	@PostMapping({ "", "/" })
	public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
		roleService.save(role);
		return new ResponseEntity<>(role, HttpStatus.CREATED);
	}

	/**
	 * This method updates the requested role
	 *
	 * @param id   Id of the role that should get updated
	 * @param role role entity to which the requested role should get updated to
	 * @return ResponseEntity with the updated role
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Role> updateById(@PathVariable Long id, @Valid @RequestBody Role role) {
		roleService.update(role);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	/**
	 * This method deletes the requested role
	 *
	 * @param id Id of the role that should be deleted
	 * @return ResponseEntity with the outcome of the deletion process
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		roleService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
