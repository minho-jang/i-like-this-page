package link.iltp.api;

import org.springframework.http.HttpStatus;

public class ApiError {
	private final String message;
	private final int status;

	ApiError(Throwable throwable, HttpStatus status) {
		this(throwable.getMessage(), status);
	}

	ApiError(String message, HttpStatus status) {
		this.message = message;
		this.status = status.value();
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}
}
