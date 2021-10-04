package link.iltp.configure;

import link.iltp.common.util.IOUtils;
import link.iltp.common.util.JsonUtils;
import link.iltp.common.util.StringUtils;
import link.iltp.common.dto.LikeRequestDto;
import link.iltp.exception.InvalidArgumentException;
import link.iltp.exception.UnsupportedMethodException;
import link.iltp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
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

public class LikeRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	TokenService tokenService;

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
		String authHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		verifyAuthorizationHeader(authHeader);
		String token = extractTokenFromAuthorizationHeader(authHeader);

		return tokenService.getUuidFromToken(token);
	}

	private static final String JWT_AUTH_PREFIX = "Bearer ";

	private String extractTokenFromAuthorizationHeader(String authHeader) {
		return authHeader.substring(JWT_AUTH_PREFIX.length());
	}

	private void verifyAuthorizationHeader(String authHeader) {
		if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(JWT_AUTH_PREFIX)) {
			throw new IllegalArgumentException("Invalid string in Authorization header.");
		}
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
		try {
			String url = "";
			ServletInputStream input = servletRequest.getInputStream();
			String body = IOUtils.inputStreamToString(input, StandardCharsets.UTF_8);
			if (StringUtils.isNotEmpty(body)) {
				Map<String, Object> map = JsonUtils.jsonStringToMap(body);
				url = (String) map.get("url");
			}
			return url;

		} catch (IOException e) {
			throw new RuntimeException("Error reading request body.");
		}
	}

	private void throwExceptionIfStringEmpty(String str, String errorMessage) {
		if (StringUtils.isEmpty(str))
			throw new InvalidArgumentException(errorMessage);
	}
}
