package link.iltp.unit.webmvctest;

import link.iltp.api.TokenController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
public class TokenControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("정상적인 GET /api/v1/token 테스트")
	public void getTokenSuccess() throws Exception {
		// given nothing
		// when
		ResultActions action = mockMvc.perform(get("/api/v1/token"));

		// then
		action.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").isString());
	}
}
