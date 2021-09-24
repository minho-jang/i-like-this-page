package com.minhojang.ilikethispagebackend.common.dto;

import lombok.Getter;

@Getter
public class UserLikeDto {
	private final long likeCount;
	private final boolean isUrlLike;

	public UserLikeDto(long likeCount, boolean isUrlLike) {
		this.likeCount = likeCount;
		this.isUrlLike = isUrlLike;
	}
}
