package link.iltp.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class InfoRestController {
	@Autowired
	private BuildProperties buildProperties;

	@GetMapping("/version")
	public ApiResult<String> version() {
		return ApiResult.success(
				"The version of i-like-this-page is " + buildProperties.getVersion()
		);
	}

	@GetMapping("/github")
	public ApiResult<String> github() {
		return ApiResult.success("https://github.com/minho-jang/i-like-this-page");
	}
}
