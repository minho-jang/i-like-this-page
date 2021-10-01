package com.minhojang.ilikethispagebackend.common.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LikeRequestDto {

	private final String url;
	private final String uuid;

	public LikeRequestDto(String uuid, String url) {
		this.url = url;
		this.uuid = uuid;
	}

}
