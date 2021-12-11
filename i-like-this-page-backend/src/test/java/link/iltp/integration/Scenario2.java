package link.iltp.integration;

import link.iltp.common.dto.LikeResponseDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Scenario2 extends ScenarioBase {
	private static final String TEST_URL = "localhost/test/1/";
	private static final String SCENE_0 = "0. 사용자는 이미 좋아요를 누른 적 있는 URL [" + TEST_URL + "]로 들어간다. 이미 iltp 위젯을 사용한 적 있기 때문에 로컬 스토리지에 토큰(iltp_tk)가 존재한다.";
	private static final String SCENE_1 = "1. URL [" + TEST_URL + "]의 좋아요 개수를 가져온다.";
	private static final String SCENE_2 = "2. URL [" + TEST_URL + "]의 좋아요를 누른다. 좋아요가 취소된다.";
	private static final String SCENE_END = "시나리오 #2 끝.";

	private static final String TEST_UUID = "00000000-0000-0000-0000-000000000000";
	private static String token = "";

	@Test
	public void scenario_2() throws Exception {
		printDescription(SCENE_0);
		init();

		printDescription(SCENE_1);
		getLikeOfUrl();

		printDescription(SCENE_2);
		unlikeThis();

		printDescription(SCENE_END);
	}

	private void init() {
		token = JWT_AUTH_PREFIX + jwtTokenProvider.generateToken(TEST_UUID);
	}

	private void getLikeOfUrl() throws Exception {
		LikeResponseDto likeResponseDto = GET_like(TEST_URL, token);

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(true));
		assertThat(likeResponseDto.getLikeCount(), is(3L));
	}

	private void unlikeThis() throws Exception {
		LikeResponseDto likeResponseDto = DELETE_like(TEST_URL, token);

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(false));
		assertThat(likeResponseDto.getLikeCount(), is(2L));
	}
}
