package api.config.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		return mapper;
	}
}
