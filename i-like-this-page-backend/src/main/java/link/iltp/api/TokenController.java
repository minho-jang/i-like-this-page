package link.iltp.api;

import link.iltp.common.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin
@Slf4j
public class TokenController {

	private final JwtTokenProvider jwtTokenProvider;

	public TokenController(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@GetMapping("")
	public ApiResult<String> getToken() {
		log.info("Get new TOKEN");
		return ApiResult.success(
				jwtTokenProvider.generateNewToken()
		);
	}
}
