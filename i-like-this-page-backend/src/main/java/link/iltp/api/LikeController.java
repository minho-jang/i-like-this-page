package link.iltp.api;

import link.iltp.common.dto.LikeRequestDto;
import link.iltp.common.dto.LikeResponseDto;
import link.iltp.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@CrossOrigin
@Slf4j
public class LikeController {

	private final LikeService likeService;

	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@GetMapping("")
	public ApiResult<LikeResponseDto> getLike(LikeRequestDto param) {
		log.info("Get LIKE: " + param);
		return ApiResult.success(
				likeService.getLike(param.getUrl(), param.getUuid())
		);
	}

	@PostMapping("")
	public ApiResult<LikeResponseDto> addLike(LikeRequestDto param) {
		log.info("Add LIKE: " + param);
		return ApiResult.success(
				likeService.saveLike(param.getUrl(), param.getUuid())
		);
	}

	@DeleteMapping("")
	public ApiResult<LikeResponseDto> cancelLike(LikeRequestDto param) {
		log.info("Cancel LIKE: " + param);
		return ApiResult.success(
				likeService.deleteLike(param.getUrl(), param.getUuid())
		);
	}
}