package link.iltp.unit.servicetest;

import link.iltp.common.dto.LikeResponseDto;
import link.iltp.entity.Like;
import link.iltp.repository.LikeRepository;
import link.iltp.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
	private final String NORMAL_URL = "localhost/test/1/";
	private final String NORMAL_UUID = "00000000-0000-0000-0000-000000000000";

	@Mock
	private LikeRepository likeRepository;

	@InjectMocks
	private LikeService likeService;

	@Test
	@DisplayName("정상적인 getLike(url, uuid) 테스트 (이전에 좋아요 누름)")
	public void getLikeGivenLikeTrue() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final Long expectLikeCount = 3L;
		final boolean expectLikeStatus = true;

		final Like like = new Like(1L, uuid, url, LocalDateTime.now());
		final List<Like> likeList = List.of(like);
		given(likeRepository.countByUrl(url)).willReturn(expectLikeCount);
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(likeList);

		// when
		LikeResponseDto result = likeService.getLike(url, uuid);

		// then
		assertThat(result.getUrl(), is(equalTo(url)));
		assertThat(result.getUuid(), is(equalTo(uuid)));
		assertThat(result.getLikeCount(), is(equalTo(expectLikeCount)));
		assertThat(result.isLikeStatus(), is(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 getLike(url, uuid) 테스트 (좋아요 누르지 않음)")
	public void getLikeGivenLikeFalse() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final Long expectLikeCount = 3L;
		final boolean expectLikeStatus = false;

		given(likeRepository.countByUrl(url)).willReturn(expectLikeCount);
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(Collections.emptyList());

		// when
		LikeResponseDto result = likeService.getLike(url, uuid);

		// then
		assertThat(result.getUrl(), is(equalTo(url)));
		assertThat(result.getUuid(), is(equalTo(uuid)));
		assertThat(result.getLikeCount(), is(equalTo(expectLikeCount)));
		assertThat(result.isLikeStatus(), is(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 saveLike(url, uuid) 테스트")
	public void saveLikeSuccess() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final long expectLikeCount = 1L;
		final boolean expectLikeStatus = true;

		final Like like = new Like(null, uuid, url, null);
		given(likeRepository.save(like)).willReturn(like);

		final Like likeSaved = new Like(1L, uuid, url, LocalDateTime.now());
		final List<Like> likeList = List.of(likeSaved);
		given(likeRepository.countByUrl(url)).willReturn(expectLikeCount);
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(likeList);

		// when
		LikeResponseDto result = likeService.saveLike(url, uuid);

		// then
		assertThat(result.getUrl(), is(equalTo(url)));
		assertThat(result.getUuid(), is(equalTo(uuid)));
		assertThat(result.getLikeCount(), is(equalTo(expectLikeCount)));
		assertThat(result.isLikeStatus(), is(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 getLikeStatus(url, uuid) 테스트 (이전에 좋아요 누름)")
	public void getLikeStatusTrue() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final List<Like> likeList = List.of(new Like(1L, uuid, url, LocalDateTime.now()));
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(likeList);

		// when
		boolean result = likeService.getLikeStatus(url, uuid);

		// then
		assertThat(result, is(true));
	}

	@Test
	@DisplayName("정상적인 getLikeStatus(url, uuid) 테스트 (좋아요 누르지 않음)")
	public void getLikeStatusFalse() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(Collections.emptyList());

		// when
		boolean result = likeService.getLikeStatus(url, uuid);

		// then
		assertThat(result, is(false));
	}

	@Test
	@DisplayName("정상적인 deleteLike(url, uuid) 테스트")
	public void deleteLikeSuccess() {
		// given
		final String url = NORMAL_URL;
		final String uuid = NORMAL_UUID;
		final long expectLikeCount = 3L;
		final boolean expectLikeStatus = false;

		given(likeRepository.countByUrl(url)).willReturn(expectLikeCount);
		given(likeRepository.findByUrlAndUuid(url, uuid)).willReturn(Collections.emptyList());

		// when
		LikeResponseDto result = likeService.deleteLike(url, uuid);

		// then
		assertThat(result.getUrl(), is(equalTo(url)));
		assertThat(result.getUuid(), is(equalTo(uuid)));
		assertThat(result.getLikeCount(), is(equalTo(expectLikeCount)));
		assertThat(result.isLikeStatus(), is(expectLikeStatus));
	}

	@Test
	@DisplayName("정상적인 countLikeOf(url) 테스트")
	public void getLikeCountOfExistedUrl() {
		// given
		final String url = NORMAL_URL;
		final Long expectLikeCount = 3L;
		given(likeRepository.countByUrl(url)).willReturn(expectLikeCount);

		// when
		Long count = likeService.countLikeOf(url);

		// then
		assertThat(count, is(equalTo(expectLikeCount)));
	}
}
