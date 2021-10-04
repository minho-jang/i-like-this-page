package link.iltp.api;

import link.iltp.common.util.IOUtils;
import link.iltp.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/content")
@Slf4j
public class ContentController {

	@GetMapping(value = "bundle-js")
	public ResponseEntity<byte[]> getBundleJs() throws IOException {
		final String bundleJsFilePath = "/iltp.bundle.min.js";

		InputStream in = getClass().getResourceAsStream(bundleJsFilePath);
		if (in != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/javascript; charset=UTF-8");
			return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} else {
			throw new NotFoundException("Can't access this file : " + bundleJsFilePath);
		}
	}
}
