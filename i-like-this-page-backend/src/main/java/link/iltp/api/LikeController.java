package link.iltp.api;

import link.iltp.common.dto.LikeRequestDto;
import link.iltp.common.dto.LikeResponseDto;
import link.iltp.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@CrossOrigin
@Slf4j
public class LikeController {

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