package link.iltp.configure;

import com.google.common.base.Strings;
import link.iltp.common.dto.LikeRequestDto;
import link.iltp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LikeRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String ERR_SERVLET_REQUEST_IS_NULL = "HttpServletRequest is null.";
	private static final String ERR_NO_UUID_IN_TOKEN = "Failed to get uuid from token.";
	private static final String ERR_INVALID_AUTHORIZATION = "Invalid string in Authorization header.";
	private static final String ERR_NO_URL_IN_REQUEST = "Failed to get URL from request.";

	private static final String JWT_AUTH_PREFIX = "Bearer ";

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
		HttpServletRequest servletRequest =
				Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
						.orElseThrow(() -> new NullPointerException(ERR_SERVLET_REQUEST_IS_NULL));

		String uuid = getUuidFromRequest(servletRequest);
		String url = getUrlFromRequest(servletRequest);

		return new LikeRequestDto(uuid, url);
	}

	private String getUuidFromRequest(HttpServletRequest servletRequest) {
		String authHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

		verifyAuthorizationHeader(authHeader);
		String token = extractTokenFromAuthorizationHeader(authHeader);
		String uuid = tokenService.getUuidFromToken(token);
		
		if (Strings.isNullOrEmpty(uuid))
			throw new IllegalStateException(ERR_NO_UUID_IN_TOKEN);

		return uuid;
	}

	private String extractTokenFromAuthorizationHeader(String authHeader) {
		return authHeader.substring(JWT_AUTH_PREFIX.length());
	}

	private void verifyAuthorizationHeader(String authHeader) {
		if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(JWT_AUTH_PREFIX)) {
			throw new IllegalArgumentException(ERR_INVALID_AUTHORIZATION);
		}
	}

	private String getUrlFromRequest(HttpServletRequest servletRequest) {
		String url = getUrlFromRequestParameter(servletRequest);

		if (Strings.isNullOrEmpty(url))
			throw new IllegalStateException(ERR_NO_URL_IN_REQUEST);

		return url;
	}

	private String getUrlFromRequestParameter(HttpServletRequest servletRequest) {
		return servletRequest.getParameter("url");
	}
}
