package com.minhojang.ilikethispagebackend.exceptions;

public class JsonException extends RuntimeException {

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

}