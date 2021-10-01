package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin
@Slf4j
public class TokenController {

	@Autowired
	TokenService tokenService;

	@GetMapping("")
	public ApiResult<String> getToken() {
		log.info("Get new token");
		return ApiResult.success(
				tokenService.generateNewToken()
		);
	}
}
