package link.iltp.integration;

import link.iltp.common.dto.LikeResponseDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Scenario1 extends ScenarioBase {
	private static final String TEST_URL = "localhost/test/1/";
	private static final String SCENE_0 = "0. URL [" + TEST_URL + "]은 현재 3개의 좋아요를 가지고 있다. 사용자는 iltp 위젯을 사용하는 페이지에 들어가 본 적이 없다.";
	private static final String SCENE_1 = "1. URL [" + TEST_URL + "]에 처음 접근하는 사용자는 로컬 스토리지에 토큰(iltp_tk)이 없다. 프론트엔드에서 토큰을 요청한다.";
	private static final String SCENE_2 = "2. URL [" + TEST_URL + "]의 좋아요 개수를 가져온다.";
	private static final String SCENE_3 = "3. URL [" + TEST_URL + "]의 좋아요 버튼을 누른다.";
	private static final String SCENE_4 = "4. URL [" + TEST_URL + "]의 좋아요 버튼을 다시 누른다. 좋아요가 취소된다.";
	private static final String SCENE_END = "시나리오 #1 끝.";

	private static String token = "";

	@Test
	public void scenario_1() throws Exception {
		printDescription(SCENE_0);

		printDescription(SCENE_1);
		getNewTokenForIltp();

		printDescription(SCENE_2);
		getLikeOfUrl();

		printDescription(SCENE_3);
		likeThisUrl();

		printDescription(SCENE_4);
		unlikeThisUrl();

		printDescription(SCENE_END);
	}

	private void getNewTokenForIltp() throws Exception {
		token = GET_token();
	}

	private void getLikeOfUrl() throws Exception {
		LikeResponseDto likeResponseDto = GET_like(TEST_URL, token);

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(false));
		assertThat(likeResponseDto.getLikeCount(), is(3L));
	}

	private void likeThisUrl() throws Exception {
		LikeResponseDto likeResponseDto = POST_like(TEST_URL, token);

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(true));
		assertThat(likeResponseDto.getLikeCount(), is(4L));
	}

	private void unlikeThisUrl() throws Exception {
		LikeResponseDto likeResponseDto = DELETE_like(TEST_URL, token);

		assertThat(likeResponseDto, is(notNullValue()));
		assertThat(likeResponseDto.isLikeStatus(), is(false));
		assertThat(likeResponseDto.getLikeCount(), is(3L));
	}
}
