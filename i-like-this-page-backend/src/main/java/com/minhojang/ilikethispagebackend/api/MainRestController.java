package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.common.dto.LikeResponseDto;
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
	public ApiResult<LikeResponseDto> getLike(LikeRequestDto param) {
		log.info("Get 'like' count of url and Get 'like' status of user: " + param);
		return ApiResult.success(
				likeService.getLike(param.getUrl(), param.getUuid())
		);
	}

	@PostMapping("/like")
	public ApiResult<LikeResponseDto> setLike(LikeRequestDto param) {
		log.info("Add 'like' to this url: " + param);
		return ApiResult.success(
				likeService.saveLike(param.getUrl(), param.getUuid())
		);
	}

	@DeleteMapping("/like")
	public ApiResult<LikeResponseDto> subtractLike(LikeRequestDto param) {
		log.info("Cancel 'like' to this url: " + param);
		return ApiResult.success(
				likeService.deleteLike(param.getUrl(), param.getUuid())
		);
	}

	@GetMapping("/check")
	public ApiResult<String> checkToken() {
		log.info("Check the token in cookie or Provide a token");
		return ApiResult.success("checkToken not implemented");
	}
}