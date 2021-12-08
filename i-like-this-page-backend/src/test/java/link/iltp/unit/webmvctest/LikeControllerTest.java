package link.iltp.unit.webmvctest;

import link.iltp.api.LikeController;
import link.iltp.common.dto.LikeResponseDto;
import link.iltp.common.util.JwtTokenProvider;
import link.iltp.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
public class LikeControllerTest {
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
	@DisplayName("정상적인 GET /api/v1/like 테스트")
	public void getLikeSuccess() throws Exception {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final long expectLikeCount = 1L;
		final boolean expectLikeStatus = true;
		final String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken(uuid);

		final LikeResponseDto responseDto = LikeResponseDto.builder()
				.url(url)
				.uuid(uuid)
				.likeCount(expectLikeCount)
				.likeStatus(expectLikeStatus)
				.build();
		given(likeService.getLike(url, uuid)).willReturn(responseDto);

		// when
		final ResultActions actions = mockMvc.perform(get("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));

		// then
		actions.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").exists())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(uuid))
				.andExpect(jsonPath("$.response.likeCount").value(expectLikeCount))
				.andExpect(jsonPath("$.response.likeStatus").value(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 POST /api/v1/like 테스트")
	public void addLikeSuccess() throws Exception {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final long expectLikeCount = 1L;
		final boolean expectLikeStatus = true;
		final String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken(uuid);

		final LikeResponseDto responseDto = LikeResponseDto.builder()
				.url(url)
				.uuid(uuid)
				.likeCount(expectLikeCount)
				.likeStatus(expectLikeStatus)
				.build();
		given(likeService.saveLike(url, uuid)).willReturn(responseDto);

		// when
		final ResultActions actions = mockMvc.perform(post("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));

		// then
		actions.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").exists())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(uuid))
				.andExpect(jsonPath("$.response.likeCount").value(expectLikeCount))
				.andExpect(jsonPath("$.response.likeStatus").value(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 DELETE /api/v1/like 테스트")
	public void cancelLikeSuccess() throws Exception {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final long expectLikeCount = 1L;
		final boolean expectLikeStatus = true;
		final String token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken(uuid);

		final LikeResponseDto responseDto = LikeResponseDto.builder()
				.url(url)
				.uuid(uuid)
				.likeCount(expectLikeCount)
				.likeStatus(expectLikeStatus)
				.build();
		given(likeService.deleteLike(url, uuid)).willReturn(responseDto);

		// when
		final ResultActions actions = mockMvc.perform(delete("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));

		// then
		actions.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value("true"))
				.andExpect(jsonPath("$.error").doesNotExist())
				.andExpect(jsonPath("$.response").exists())
				.andExpect(jsonPath("$.response.url").value(url))
				.andExpect(jsonPath("$.response.uuid").value(uuid))
				.andExpect(jsonPath("$.response.likeCount").value(expectLikeCount))
				.andExpect(jsonPath("$.response.likeStatus").value(expectLikeStatus));
	}
}
