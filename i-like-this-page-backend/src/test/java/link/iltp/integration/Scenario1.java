package link.iltp.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import link.iltp.api.ApiResult;
import link.iltp.common.dto.LikeResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Scenario1 extends ScenarioBase {
	private static final String TEST_URL = "localhost/test/1/";
	private static final String SCENE_0 = "0. URL [" + TEST_URL + "]은 현재 3개의 좋아요를 가지고 있다. 사용자는 iltp 위젯을 사용하는 페이지에 들어가 본 적이 없다.";
	private static final String SCENE_1 = "1. URL [" + TEST_URL + "]에 처음 접근하는 사용자는 로컬 스토리지에 토큰(iltp_tk)이 없다. 프론트엔드에서 토큰을 요청한다.";
	private static final String SCENE_2 = "2. URL [" + TEST_URL + "]의 좋아요 개수를 가져온다.";
	private static final String SCENE_3 = "3. URL [" + TEST_URL + "] iltp 위젯의 좋아요 버튼을 누른다.";
	private static final String SCENE_4 = "4. URL [" + TEST_URL + "] iltp 위젯의 좋아요 버튼을 다시 누른다. 좋아요가 취소된다.";
	private static final String SCENE_END = "시나리오 #1 끝.";

	private static String token = "";

	@Test
	public void scenario_1() throws Exception {
		printDescription(SCENE_0);

		printDescription(SCENE_1);
		getNewToken();

		printDescription(SCENE_2);
		getLikeOf(TEST_URL);

		printDescription(SCENE_3);
		likeThis(TEST_URL);

		printDescription(SCENE_4);
		unlikeThis(TEST_URL);

		printDescription(SCENE_END);
	}

	private void getNewToken() throws Exception {
		ResultActions action = mockMvc.perform(get("/api/v1/token"));
		MvcResult result = action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn();
		String body = result.getResponse().getContentAsString();

		ApiResult<String> apiResult = convertJsonStringToObject(body, new TypeReference<ApiResult<String>>() {
		});
		String newToken = apiResult.getResponse();

		token = JWT_AUTH_PREFIX + newToken;
		assertThat(token, is(not(emptyString())));
	}

	private void getLikeOf(final String url) throws Exception {
		ResultActions action = mockMvc.perform(get("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));
		MvcResult result = action
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn();
		String body = result.getResponse().getContentAsString();

		ApiResult<LikeResponseDto> apiResult = convertJsonStringToObject(body, new TypeReference<>() {
		});

		LikeResponseDto likeResponseDto = apiResult.getResponse();

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(false));
		assertThat(likeResponseDto.getLikeCount(), is(3L));
	}

	private void likeThis(final String url) throws Exception {
		ResultActions action = mockMvc.perform(post("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));

		MvcResult result = action.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn();
		String body = result.getResponse().getContentAsString();
		ApiResult<LikeResponseDto> apiResult = convertJsonStringToObject(body, new TypeReference<>() {
		});

		LikeResponseDto likeResponseDto = apiResult.getResponse();

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(true));
		assertThat(likeResponseDto.getLikeCount(), is(4L));
	}

	private void unlikeThis(final String url) throws Exception {
		ResultActions action = mockMvc.perform(delete("/api/v1/like")
				.param("url", url)
				.header("Authorization", token));

		MvcResult result = action.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn();

		String body = result.getResponse().getContentAsString();
		ApiResult<LikeResponseDto> apiResult = convertJsonStringToObject(body, new TypeReference<>() {
		});

		LikeResponseDto likeResponseDto = apiResult.getResponse();

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(false));
		assertThat(likeResponseDto.getLikeCount(), is(3L));
	}
}
