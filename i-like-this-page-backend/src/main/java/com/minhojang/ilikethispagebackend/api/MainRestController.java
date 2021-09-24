package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.common.dto.UserLikeDto;
import com.minhojang.ilikethispagebackend.common.dto.LikeRequestDto;
import com.minhojang.ilikethispagebackend.service.LikeService;
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
	public ApiResult<UserLikeDto> getLike(LikeRequestDto param) {
		log.info("Get 'like' count of url and Get 'like' status of user with this ip address: " + param);
		return ApiResult.success(
				likeService.getLike(param.getUrl(), param.getClientIp())
		);
	}

	@PostMapping("/like")
	public ApiResult<UserLikeDto> setLike(LikeRequestDto param) {
		log.info("Add 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.saveLike(param.getUrl(), param.getClientIp())
		);
	}

	@DeleteMapping("/like")
	public ApiResult<UserLikeDto> subtractLike(LikeRequestDto param) {
		log.info("Cancel 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.deleteLike(param.getUrl(), param.getClientIp())
		);
	}
}