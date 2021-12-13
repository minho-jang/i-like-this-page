package link.iltp.unit.webmvctest;

import link.iltp.api.InfoController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InfoController.class)
public class InfoControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BuildProperties buildProperties;

	@Test
	@DisplayName("정상적인 GET /version 테스트")
	public void getVersionSuccess() throws Exception {
		// given
		final String expectResponse = "The version of i-like-this-page is 0.0.1";
		given(buildProperties.getVersion()).willReturn("0.0.1");

		// when
		ResultActions action = mockMvc.perform(get("/version"));

		// then
		action.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").isString())
				.andExpect(jsonPath("$.response").value(expectResponse));
	}

	@Test
	@DisplayName("정상적인 GET /github 테스트")
	public void getGithubSuccess() throws Exception {
		// given
		final String expectResponse = "https://github.com/minho-jang/i-like-this-page";

		// when
		ResultActions action = mockMvc.perform(get("/github"));

		// then
		action.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").isString())
				.andExpect(jsonPath("$.response").value(expectResponse));
	}
}
