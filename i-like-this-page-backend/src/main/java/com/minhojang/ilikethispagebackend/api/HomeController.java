package com.minhojang.ilikethispagebackend.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class HomeController {
	@Autowired
	private BuildProperties buildProperties;

	@GetMapping("/version")
	public ApiResult<String> version() {
		return ApiResult.success(
				"I LIKE THIS PAGE - " + buildProperties.getVersion()
		);
	}
}
