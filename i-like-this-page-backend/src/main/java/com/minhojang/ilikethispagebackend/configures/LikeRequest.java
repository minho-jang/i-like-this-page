package com.minhojang.ilikethispagebackend.configures;

import lombok.Getter;

@Getter
public class LikeRequest implements Client {

	private final String clientIp;
	private final String url;

	public LikeRequest(String clientIp, String url) {
		this.clientIp = clientIp;
		this.url = url;
	}
}
