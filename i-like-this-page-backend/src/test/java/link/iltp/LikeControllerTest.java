package link.iltp;

import link.iltp.service.TokenService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LikeController 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LikeControllerTest {

	@Autowired
	MockMvc mockmvc;

	@Autowired
	TokenService tokenService;

	private static String TEMP_UUID;

	@BeforeAll
	static void init() {
		TEMP_UUID = UUID.randomUUID().toString();
	}

	@DisplayName("POST /api/v1/like 를 호출한다.")
	@Test
	@Order(1)
	public void postLikeTest() throws Exception {
		String token = tokenService.generateToken(TEMP_UUID);
		token = "Bearer " + token;

		String url = "naver.com";

		this.mockmvc.perform(post("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(TEMP_UUID))
				.andExpect(jsonPath("$.response.likeCount").value(1))
				.andExpect(jsonPath("$.response.likeStatus").value(true))
				.andDo(print());
	}

	@DisplayName("GET /api/v1/like 를 호출한다.")
	@Test
	@Order(2)
	public void getLikeTest() throws Exception {
		String token = tokenService.generateToken(TEMP_UUID);
		token = "Bearer " + token;

		// already saved "naver.com" above postLikeTest().
		String url = "naver.com";

		this.mockmvc.perform(get("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(TEMP_UUID))
				.andExpect(jsonPath("$.response.likeCount").value(1))
				.andExpect(jsonPath("$.response.likeStatus").value(true))
				.andDo(print());
	}

	@DisplayName("DELETE /api/v1/like 를 호출한다.")
	@Test
	@Order(3)
	public void deleteLikeTest() throws Exception {
		String token = tokenService.generateToken(TEMP_UUID);
		token = "Bearer " + token;

		String url = "naver.com";

		this.mockmvc.perform(delete("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(TEMP_UUID))
				.andExpect(jsonPath("$.response.likeCount").value(0))
				.andExpect(jsonPath("$.response.likeStatus").value(false))
				.andDo(print());
	}
}
