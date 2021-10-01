package com.minhojang.ilikethispagebackend.api;

import com.minhojang.ilikethispagebackend.common.dto.LikeRequestDto;
import com.minhojang.ilikethispagebackend.common.dto.LikeResponseDto;
import com.minhojang.ilikethispagebackend.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@CrossOrigin
@Slf4j
public class LikeRestController {

	@Autowired
	LikeService likeService;

	@GetMapping("")
	public ApiResult<LikeResponseDto> getLike(LikeRequestDto param) {
		log.info("Get 'like' count of url and Get 'like' status of user with this ip address: " + param);
		return ApiResult.success(
				likeService.getLike(param.getUrl(), param.getUuid())
		);
	}

	@PostMapping("")
	public ApiResult<LikeResponseDto> setLike(LikeRequestDto param) {
		log.info("Add 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.saveLike(param.getUrl(), param.getUuid())
		);
	}

	@DeleteMapping("")
	public ApiResult<LikeResponseDto> subtractLike(LikeRequestDto param) {
		log.info("Cancel 'like' to this url for this ip address: " + param);
		return ApiResult.success(
				likeService.deleteLike(param.getUrl(), param.getUuid())
		);
	}
}