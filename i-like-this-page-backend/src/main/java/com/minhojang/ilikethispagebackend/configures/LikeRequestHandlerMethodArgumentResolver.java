package com.minhojang.ilikethispagebackend.configures;

import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LikeRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
  private String urlParameterName = "url";

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
    return LikeUrl.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
          MethodParameter parameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory
  ) {
    String url = webRequest.getParameter(urlParameterName);
    String clientIp = getClientIpFromRequest(webRequest);

    // TODO: url, clientIp validation check
    // TODO: url에서 "http://" 빼기

    return new LikeRequest(clientIp, url);
  }

  private String getClientIpFromRequest(NativeWebRequest webRequest) {
    HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);

    // TODO: req can be null

    for (String header : IP_HEADER_CANDIDATES) {
      String ip = req.getHeader(header);

      if (ip != null && ip.length() != 0 && "unknown".equals(ip)) {
        return ip;
      }
    }

    return req.getRemoteAddr();
  }
}
