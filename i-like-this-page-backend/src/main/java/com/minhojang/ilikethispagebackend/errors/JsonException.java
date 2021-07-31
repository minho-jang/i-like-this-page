package com.minhojang.ilikethispagebackend.errors;

public class JsonException extends RuntimeException {

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

}