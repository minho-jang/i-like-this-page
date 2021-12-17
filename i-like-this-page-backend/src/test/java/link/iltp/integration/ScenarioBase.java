package link.iltp.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import link.iltp.api.ApiResult;
import link.iltp.common.dto.LikeResponseDto;
import link.iltp.common.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScenarioBase {
	protected static final String JWT_AUTH_PREFIX = "Bearer ";

	@Autowired
	protected JwtTokenProvider jwtTokenProvider;

	@Autowired
	protected MockMvc mockMvc;

	private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

	public <T> ApiResult<T> convertJsonStringToApiResult(String jsonString, Class<T> responseType) {
		try {
			return objectMapper.readValue(jsonString, objectMapper.getTypeFactory()
					.constructParametricType(ApiResult.class, responseType));
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Something wrong with reading the json string.", e);
		}
	}

	public void printDescription(String description) {
		System.out.println();
		System.out.println(">> " + description);
		System.out.println();
	}

	public String GET_token() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/token"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.response").exists())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		ApiResult<String> apiResult = convertJsonStringToApiResult(content, String.class);

		assertThat(apiResult.getResponse(), is(not(emptyString())));
		return JWT_AUTH_PREFIX + apiResult.getResponse();
	}

	public LikeResponseDto GET_like(final String url, final String token) throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.response").exists())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		ApiResult<LikeResponseDto> apiResult = convertJsonStringToApiResult(content, LikeResponseDto.class);

		assertThat(apiResult.getResponse(), is(notNullValue()));
		return apiResult.getResponse();
	}

	public LikeResponseDto POST_like(final String url, final String token) throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.response").exists())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		ApiResult<LikeResponseDto> apiResult = convertJsonStringToApiResult(content, LikeResponseDto.class);

		assertThat(apiResult.getResponse(), is(notNullValue()));
		return apiResult.getResponse();
	}

	public LikeResponseDto DELETE_like(final String url, final String token) throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/api/v1/like")
						.param("url", url)
						.header("Authorization", token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.response").exists())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		ApiResult<LikeResponseDto> apiResult = convertJsonStringToApiResult(content, LikeResponseDto.class);

		assertThat(apiResult.getResponse(), is(notNullValue()));
		return apiResult.getResponse();
	}

}
