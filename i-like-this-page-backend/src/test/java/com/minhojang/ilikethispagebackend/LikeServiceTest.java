package com.minhojang.ilikethispagebackend;

import com.minhojang.ilikethispagebackend.entity.Like;
import com.minhojang.ilikethispagebackend.repository.LikeRepository;
import com.minhojang.ilikethispagebackend.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("LikeService 테스트")
@SpringBootTest
public class LikeServiceTest {

	@Autowired
	private LikeService likeService;

	@Autowired
	private LikeRepository likeRepository;

	@DisplayName("해당 URL과 IP주소와 함께 좋아요를 저장한다.")
	@Test
	public void saveLike() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";
		String ipAddress = "0:0:0:0:0:0:0:1";

		// when
		likeService.saveLike(url, ipAddress);

		// then
		Like like = likeRepository.findAll().get(0);
		assertEquals(like.getUrl(), url);
		assertEquals(like.getIpAddress(), ipAddress);
	}

	@DisplayName("해당 URL에 대한 좋아요를 개수를 센다.")
	@Test
	public void getTheNumberOfLikesOfUrl() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";

		// when
		Long result = likeService.countLikeOf(url);

		// then
		assertEquals(1, result);
	}

	@DisplayName("해당 URL과 IP주소를 가지는 좋아요를 삭제한다.")
	@Test
	public void deleteLikeOfUrl() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";
		String ipAddress = "0:0:0:0:0:0:0:1";
		assertTrue(likeService.isLikeUrl(url, ipAddress));

		// when
		likeService.deleteLike(url, ipAddress);

		// then
		assertTrue(likeRepository.findAll().isEmpty());
	}
}
