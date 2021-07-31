package com.minhojang.ilikethispagebackend.services;

import com.minhojang.ilikethispagebackend.api.UserLikeVo;
import com.minhojang.ilikethispagebackend.models.Like;
import com.minhojang.ilikethispagebackend.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	public Long countLikeOf(String url) {
		return likeRepository.countByUrl(url);
	}

	public Boolean isLikeUrl(String url, String ipAddress) {
		List<Like> result = likeRepository.findByUrlAndIpAddress(url, ipAddress);
		return result.size() > 0;
	}

	public UserLikeVo getLike(String url, String ipAddress) {
		return new UserLikeVo(countLikeOf(url), isLikeUrl(url, ipAddress));
	}

	@Transactional
	public UserLikeVo saveLike(String url, String ipAddress) {
		likeRepository.save(new Like(null, ipAddress, url, null));
		return getLike(url, ipAddress);
	}
}
