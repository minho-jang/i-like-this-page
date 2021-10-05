package link.iltp;

import link.iltp.entity.Like;
import link.iltp.repository.LikeRepository;
import link.iltp.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LikeService 테스트")
@SpringBootTest
public class LikeServiceTest {

	@Autowired
	private LikeService likeService;

	@Autowired
	private LikeRepository likeRepository;

	@DisplayName("해당 URL과 UUID로 '좋아요'를 저장한다")
	@Test
	public void saveLike() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";
		String uuid = "uuid-for-test";

		// when
		likeService.saveLike(url, uuid);

		// then
		Like like = likeRepository.findAll().get(0);
		assertEquals(like.getUrl(), url);
		assertEquals(like.getUuid(), uuid);
	}

	@DisplayName("해당 URL에 대한 좋아요를 개수를 센다")
	@Test
	public void getTheNumberOfLikesOfUrl() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";

		// when
		Long result = likeService.countLikeOf(url);

		// then
		assertEquals(1, result);
	}

	@DisplayName("해당 URL과 UUID에 대한 '좋아요'를 삭제한다")
	@Test
	public void deleteLikeOfUrl() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";
		String uuid = "uuid-for-test";
		assertTrue(likeService.getLikeStatus(url, uuid));

		// when
		likeService.deleteLike(url, uuid);

		// then
		assertTrue(likeRepository.findAll().isEmpty());
	}
}
