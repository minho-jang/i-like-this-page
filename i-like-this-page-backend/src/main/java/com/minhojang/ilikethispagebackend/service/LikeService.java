package com.minhojang.ilikethispagebackend.service;

import com.minhojang.ilikethispagebackend.common.dto.LikeResponseDto;
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

	public boolean getLikeStatus(String url, String uuid) {
		List<Like> result = likeRepository.findByUrlAndUuid(url, uuid);
		return result.size() > 0;
	}

	public LikeResponseDto getLike(String url, String uuid) {
		return LikeResponseDto.builder()
				.url(url)
				.uuid(uuid)
				.likeCount(countLikeOf((url)))
				.likeStatus(getLikeStatus(url, uuid))
				.build();
	}

	@Transactional
	public LikeResponseDto saveLike(String url, String uuid) {
		likeRepository.save(new Like(null, uuid, url, null));
		return getLike(url, uuid);
	}

	@Transactional
	public LikeResponseDto deleteLike(String url, String uuid) {
		likeRepository.deleteByUrlAndUuid(url, uuid);
		return getLike(url, uuid);
	}
}
