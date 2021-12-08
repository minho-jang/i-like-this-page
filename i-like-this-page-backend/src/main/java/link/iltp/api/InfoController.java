package link.iltp.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class InfoController {

	private final BuildProperties buildProperties;

	public InfoController(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@GetMapping("/version")
	public ApiResult<String> getVersion() {
		return ApiResult.success(
				"The version of i-like-this-page is " + buildProperties.getVersion()
		);
	}

	@GetMapping("/github")
	public ApiResult<String> getGithub() {
		return ApiResult.success("https://github.com/minho-jang/i-like-this-page");
	}
}
