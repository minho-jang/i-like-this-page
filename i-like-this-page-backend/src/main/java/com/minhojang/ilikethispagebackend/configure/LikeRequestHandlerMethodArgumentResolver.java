package com.minhojang.ilikethispagebackend.configure;

import com.minhojang.ilikethispagebackend.common.util.IOUtils;
import com.minhojang.ilikethispagebackend.common.util.JsonUtils;
import com.minhojang.ilikethispagebackend.common.util.StringUtils;
import com.minhojang.ilikethispagebackend.common.dto.LikeRequestDto;
import com.minhojang.ilikethispagebackend.exception.InvalidArgumentException;
import com.minhojang.ilikethispagebackend.exception.UnsupportedMethodException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LikeRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return LikeRequestDto.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory
	) {
		return createLikeRequest(webRequest);
	}

	private LikeRequestDto createLikeRequest(NativeWebRequest webRequest) {
		Optional<HttpServletRequest> webRequestNullable =
				Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class));
		HttpServletRequest servletRequest =
				webRequestNullable.orElseThrow(() -> new RuntimeException("HttpServletRequest must not be null"));

		String uuid = getUuidFromRequest(servletRequest);
		String url = getUrlFromRequest(servletRequest);

		throwExceptionIfStringEmpty(uuid, "UUID cannot be empty.");
		throwExceptionIfStringEmpty(url, "URL cannot be empty.");

		return new LikeRequestDto(uuid, url);
	}

	private String getUuidFromRequest(HttpServletRequest servletRequest) {
		return UUID.randomUUID().toString();	// TODO: authorization header 에서 jwt 읽어오기
	}

	private String getUrlFromRequest(HttpServletRequest servletRequest) {
		String method = servletRequest.getMethod();
		if ("GET".equals(method)) {
			return getUrlFromGetRequest(servletRequest);

		} else if ("POST".equals(method) || "DELETE".equals(method)) {
			return getUrlFromPostRequest(servletRequest);

		} else {
			throw new UnsupportedMethodException(method + " is an unsupported method");
		}
	}

	private String getUrlFromGetRequest(HttpServletRequest servletRequest) {
		return servletRequest.getParameter("url");
	}

	private String getUrlFromPostRequest(HttpServletRequest servletRequest) {
		String url = "";
		try {
			ServletInputStream input = servletRequest.getInputStream();
			String body = IOUtils.inputStreamToString(input, StandardCharsets.UTF_8);
			if (StringUtils.isNotEmpty(body)) {
				Map<String, Object> map = JsonUtils.jsonStringToMap(body);
				url = (String) map.get("url");
			}

		} catch (IOException e) {
			throw new RuntimeException("Error reading request body.");
		}

		return url;
	}

	private void throwExceptionIfStringEmpty(String str, String errorMessage) {
		if (StringUtils.isEmpty(str))
			throw new InvalidArgumentException(errorMessage);
	}
}
