package api.webContext.domain.address;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.Application;
import api.config.mapping.MappableRegisterRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
	
	@InjectMocks
	private AddressController addressController;
	
	@Mock
	private AddressService addressService;
	
	private MockMvc mockMvc;
	
	private Address addressWorblaufen;
	
	private Address addressZuerich;
	
	private List<Address> addresses;
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
		
		// Setup Addresses
		addressZuerich = new Address(1L, "Zürich", "Herostrasse", "12", "8038");
		addressWorblaufen = new Address(2L, "Worblaufen", "Worblaufenstrasse", "200", "3048");
		
		addresses = new ArrayList<>();
		addresses.add(addressWorblaufen);
		addresses.add(addressZuerich);
		
		// Register all Models and s for the mapping
		MappableRegisterRunner mappableRegisterRunner = new MappableRegisterRunner();
		mappableRegisterRunner.run(null);
	}
	
	@Test
	public void getById_givenId_returnsAddress() throws JsonProcessingException, Exception {
		when(addressService.findById(addressWorblaufen.getId())).thenReturn(addressWorblaufen);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{id}", addressWorblaufen.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(addressWorblaufen)));
	}
	
	@Test
	public void getAll_returnsAddressList() throws JsonProcessingException, Exception {
		when(addressService.findAll()).thenReturn(addresses);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/addresses")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(addresses)));
	}
	
	@Test
	public void create_givenAddress_returnsAddress() throws JsonProcessingException, Exception {
		doNothing().when(addressService).save(addressWorblaufen);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/addresses").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(addressWorblaufen))).andExpect(status().isCreated())
				.andExpect(content().json(new ObjectMapper().writeValueAsString(addressWorblaufen)));
	}
}
