package link.iltp.configure;

import link.iltp.common.util.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

	@Bean
	public JwtTokenProvider jwtTokenProvider() {
		return new JwtTokenProvider();
	}

	@Bean
	public LikeRequestHandlerMethodArgumentResolver LikeRequestHandlerMethodArgumentResolver() {
		return new LikeRequestHandlerMethodArgumentResolver(jwtTokenProvider());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(LikeRequestHandlerMethodArgumentResolver());
	}
}
