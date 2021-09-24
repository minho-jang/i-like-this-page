package com.minhojang.ilikethispagebackend.service;

import com.minhojang.ilikethispagebackend.common.dto.UserLikeDto;
import com.minhojang.ilikethispagebackend.entity.Like;
import com.minhojang.ilikethispagebackend.repository.LikeRepository;
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

	public UserLikeDto getLike(String url, String ipAddress) {
		return new UserLikeDto(countLikeOf(url), isLikeUrl(url, ipAddress));
	}

	@Transactional
	public UserLikeDto saveLike(String url, String ipAddress) {
		likeRepository.save(new Like(null, ipAddress, url, null));
		return getLike(url, ipAddress);
	}

	@Transactional
	public UserLikeDto deleteLike(String url, String ipAddress) {
		likeRepository.deleteByUrlAndIpAddress(url, ipAddress);
		return getLike(url, ipAddress);
	}
}
