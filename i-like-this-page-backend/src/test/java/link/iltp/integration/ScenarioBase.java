package link.iltp.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import link.iltp.common.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScenarioBase {
	protected static final String JWT_AUTH_PREFIX = "Bearer ";

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JwtTokenProvider jwtTokenProvider;

	@Autowired
	protected ObjectMapper objectMapper;

	protected <T> T convertJsonStringToObject(String jsonString, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(jsonString, typeReference);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Something wrong with reading the json string.");
		}
	}

	protected void printDescription(String description) {
		System.out.println();
		System.out.println(">> " + description);
		System.out.println();
	}
}
