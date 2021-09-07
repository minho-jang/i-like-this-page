package com.minhojang.ilikethispagebackend;

import com.minhojang.ilikethispagebackend.models.Like;
import com.minhojang.ilikethispagebackend.repositories.LikeRepository;
import com.minhojang.ilikethispagebackend.services.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LikeServiceTest {

	@Autowired
	private LikeService likeService;

	@Autowired
	private LikeRepository likeRepository;

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

	@Test
	public void getTheNumberOfLikesOfUrl() {
		// given
		String url = "127.0.0.1:5500/i-like-this-page-frontend/prototype.html";

		// when
		Long result = likeService.countLikeOf(url);

		// then
		assertEquals(1, result);
	}

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
