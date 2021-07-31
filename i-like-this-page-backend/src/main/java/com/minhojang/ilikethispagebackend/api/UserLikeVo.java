package com.minhojang.ilikethispagebackend.api;

import lombok.Getter;

@Getter
public class UserLikeVo {
	private final long likeCount;
	private final boolean isUrlLike;

	public UserLikeVo(long likeCount, boolean isUrlLike) {
		this.likeCount = likeCount;
		this.isUrlLike = isUrlLike;
	}
}
