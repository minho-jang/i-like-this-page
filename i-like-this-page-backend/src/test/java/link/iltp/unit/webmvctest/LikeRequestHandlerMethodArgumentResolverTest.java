package link.iltp.unit.webmvctest;

import link.iltp.api.LikeController;
import link.iltp.common.util.JwtTokenProvider;
import link.iltp.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
public class LikeRequestHandlerMethodArgumentResolverTest {
	private final String JWT_AUTH_PREFIX = "Bearer ";
	private final String NORMAL_URL = "localhost/test/1/";
	private final String NORMAL_UUID = "00000000-0000-0000-0000-000000000000";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private LikeService likeService;

	@Test
	@DisplayName("URL이 없을 때, POST /api/v1/like 테스트")
	public void addLikeWithoutUrl() throws Exception {
		// given
		final String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken(NORMAL_UUID);

		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.header("Authorization", token));

		// then
		actions.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.success").value("false"))
				.andExpect(jsonPath("$.response").doesNotExist())
				.andExpect(jsonPath("$.error").exists());
	}

	@Test
	@DisplayName("authorization이 없을 때, POST /api/v1/like 테스트")
	public void addLikeWithoutAuth() throws Exception {
		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.param("url", NORMAL_URL));

		// then
		actions.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.success").value("false"))
				.andExpect(jsonPath("$.response").doesNotExist())
				.andExpect(jsonPath("$.error").exists());
	}

	@Test
	@DisplayName("authorization이 부적절할 때, POST /api/v1/like 테스트")
	public void addLikeWithInvalidAuth() throws Exception {
		// given
		final String token = JWT_AUTH_PREFIX + "ANY_INVALID_TOKEN";

		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.param("url", NORMAL_URL)
				.header("Authorization", token));

		// then
		actions.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.success").value("false"))
				.andExpect(jsonPath("$.response").doesNotExist())
				.andExpect(jsonPath("$.error").exists());
	}

	@Test
	@DisplayName("authorization prefix 부적절할 때, POST /api/v1/like 테스트")
	public void addLikeWithInvalidAuthPrefix() throws Exception {
		// given
		final String token = "TEMP-PREFIX" + jwtTokenProvider.generateToken(NORMAL_UUID);

		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.param("url", NORMAL_URL)
				.header("Authorization", token));

		// then
		actions.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.success").value("false"))
				.andExpect(jsonPath("$.response").doesNotExist())
				.andExpect(jsonPath("$.error").exists());
	}

	@Test
	@DisplayName("토큰은 맞지만 UUID가 없을 때, POST /api/v1/like 테스트")
	public void addLikeWithValidAuthNoUuid() throws Exception {
		// given
		final String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken("");

		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.param("url", NORMAL_URL)
				.header("Authorization", token));

		// then
		actions.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.success").value("false"))
				.andExpect(jsonPath("$.response").doesNotExist())
				.andExpect(jsonPath("$.error").exists());
	}
}
