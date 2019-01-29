package api.webContext.domain.address;

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

import api.webContext.domain.address.Address;
import api.webContext.domain.address.AddressService;

public class AddressController {

	private AddressService addressService;

	/**
	 *
	 */
	public AddressController() {
	}

	/**
	 * @param addressService
	 */
	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	/**
	 * This method returns the requested address
	 *
	 * @param id Id of the requested address
	 * @return ResponseEntity with the address that was requested
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Address> getById(@PathVariable Long id) {
		Address address = addressService.findById(id);
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	/**
	 * This method returns all addresses
	 *
	 * @return ResponseEntity with all existing addresses
	 */
	@GetMapping({ "", "/" })
	public @ResponseBody ResponseEntity<List<Address>> getAll() {
		List<Address> addresss = addressService.findAll();
		return new ResponseEntity<>(addresss, HttpStatus.OK);
	}

	/**
	 * This method creates an address
	 *
	 * @return ResponseEntity with the address that was created
	 */
	@PostMapping({ "", "/" })
	public ResponseEntity<Address> create(@Valid @RequestBody Address address) {
		addressService.save(address);
		return new ResponseEntity<>(address, HttpStatus.CREATED);
	}

	/**
	 * This method updates the requested address
	 *
	 * @param id   Id of the address that should get updated
	 * @param address address entity to which the requested address should get updated to
	 * @return ResponseEntity with the updated address
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateById(@PathVariable Long id, @Valid @RequestBody Address address) {
		addressService.update(address);
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	/**
	 * This method deletes the requested address
	 *
	 * @param id Id of the address that should be deleted
	 * @return ResponseEntity with the outcome of the deletion process
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		addressService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
