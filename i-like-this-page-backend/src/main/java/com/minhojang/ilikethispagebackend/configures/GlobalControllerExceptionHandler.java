package com.minhojang.ilikethispagebackend.configures;

import com.minhojang.ilikethispagebackend.api.ApiResult;
import com.minhojang.ilikethispagebackend.exceptions.InvalidArgumentException;
import com.minhojang.ilikethispagebackend.exceptions.JsonException;
import com.minhojang.ilikethispagebackend.exceptions.NotFoundException;
import com.minhojang.ilikethispagebackend.exceptions.UnsupportedMethodException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.minhojang.ilikethispagebackend.api.ApiResult.error;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

	private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
		return newResponse(throwable.getMessage(), status);
	}

	private ResponseEntity<ApiResult<?>> newResponse(String message, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<>(error(message, status), headers, status);
	}

	@ExceptionHandler({
			JsonException.class,
			InvalidArgumentException.class,
			UnsupportedMethodException.class
	})
	public ResponseEntity<?> handleBadRequestException(Exception e) {
		return newResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(Exception e) {
		return newResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({
			Exception.class,
			RuntimeException.class
	})
	public ResponseEntity<?> handleException(Exception e) {
		log.error("Unexpected exception occurred: {}", e.getMessage(), e);
		return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
