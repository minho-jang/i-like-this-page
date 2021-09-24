package com.minhojang.ilikethispagebackend.common.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LikeRequestDto implements Client {

	private final String clientIp;
	private final String url;

	public LikeRequestDto(String clientIp, String url) {
		this.clientIp = clientIp;
		this.url = url;
	}

}
