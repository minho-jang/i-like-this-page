package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.configures.LikeRequest;
import com.minhojang.ilikethispagebackend.services.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
public class MainRestController {

	@Autowired
	LikeService likeService;

	@GetMapping("/like")
	public ApiResult<UserLikeVo> getLike(LikeRequest param) {
		log.info("Get 'like' count of url and Get 'like' status of user with this ip address: " + param);
		return ApiResult.success(
				likeService.getLike(param.getUrl(), param.getClientIp())
		);
	}

	@PostMapping("/like")
	public ApiResult<UserLikeVo> setLike(LikeRequest param) {
		log.info("Add 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.saveLike(param.getUrl(), param.getClientIp())
		);
	}

	@DeleteMapping("/like")
	public ApiResult<UserLikeVo> subtractLike(LikeRequest param) {
		log.info("Cancel 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.deleteLike(param.getUrl(), param.getClientIp())
		);
	}
}