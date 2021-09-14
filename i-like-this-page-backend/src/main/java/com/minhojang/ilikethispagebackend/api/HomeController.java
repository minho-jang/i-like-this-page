package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.configures.LikeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class HomeController {

	@GetMapping("/version")
	public ApiResult<String> helloWorld(LikeRequest param) {
		return ApiResult.success(
				"I LIKE THIS PAGE - 0.0.1v"
		);
	}
}
