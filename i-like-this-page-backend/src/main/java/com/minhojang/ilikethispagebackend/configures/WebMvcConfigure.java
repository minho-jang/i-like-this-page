package com.minhojang.ilikethispagebackend.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

	@Bean
	public LikeRequestHandlerMethodArgumentResolver LikeRequestHandlerMethodArgumentResolver() {
		return new LikeRequestHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(LikeRequestHandlerMethodArgumentResolver());
	}
}
