package com.minhojang.ilikethispagebackend.configures;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class ClientIpResolver implements HandlerMethodArgumentResolver {
  private static final String[] IP_HEADER_CANDIDATES = {
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
    return parameter.hasParameterAnnotation(ClientIp.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);

    for (String header : IP_HEADER_CANDIDATES) {
      String ip = req.getHeader(header);

      if (ip != null && ip.length() != 0 && "unknown".equals(ip)) {
        return ip;
      }
    }

    return req.getRemoteAddr();
  }
}
