package com.minhojang.ilikethispagebackend.configures;

import com.minhojang.ilikethispagebackend.errors.InvalidArgumentException;

public class LikeRequest implements Client {

  private final String clientIp;
  private final String url;

  public LikeRequest(String clientIp, String url) {
    checkString(clientIp, "Client IP cannot be empty.");
    checkString(url, "URL cannot be empty.");

    this.clientIp = clientIp;
    this.url = url;
  }

  private void checkString(String str, String errorMessage) {
    if (str == null || "".equals(str))
      throw new InvalidArgumentException(errorMessage);
  }

  @Override
  public String getClientIp() {
    return this.clientIp;
  }

  @Override
  public String getUrl() {
    return this.url;
  }
}
