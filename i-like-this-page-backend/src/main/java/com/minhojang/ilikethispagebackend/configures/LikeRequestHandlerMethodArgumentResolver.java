package com.minhojang.ilikethispagebackend.configures;

import com.minhojang.ilikethispagebackend.errors.InvalidArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LikeRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
  private final String[] IP_HEADER_CANDIDATES = {
          "X-Forwarded-For",
          "Proxy-Client-IP",
          "WL-Proxy-Client-IP",
          "HTTP_X_FORWARDED_FOR",
          "HTTP_X_FORWARDED",
          "HTTP_X_CLUSTER_CLIENT_IP",
          "HTTP_CLIENT_IP",
          "HTTP_FORWARDED_FOR",
          "HTTP_FORWARDED",
          "HTTP_VIA",
          "REMOTE_ADDR"
  };

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Client.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
          MethodParameter parameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory
  ) {

    String url = webRequest.getParameter("url");
    String clientIp = getClientIpFromRequest(webRequest);

    checkString(clientIp, "Client IP cannot be empty.");
    checkString(url, "URL cannot be empty.");

    return new LikeRequest(clientIp, url);
  }

  private void checkString(String str, String errorMessage) {
    if (str == null || "".equals(str))
      throw new InvalidArgumentException(errorMessage);
  }

  private String getClientIpFromRequest(NativeWebRequest webRequest) {
    HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
    if (req == null)
      throw new RuntimeException("HttpServletRequest must not be null");

    for (String header : IP_HEADER_CANDIDATES) {
      String ip = req.getHeader(header);

      if (ip != null && ip.length() != 0 && !"unknown".equals(ip)) {
        return ip;
      }
    }

    return req.getRemoteAddr();
  }
}
