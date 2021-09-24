package com.minhojang.ilikethispagebackend.exception;

public class UnsupportedMethodException extends RuntimeException {

	public UnsupportedMethodException(String message) {
		super(message);
	}

	public UnsupportedMethodException(String message, Throwable cause) {
		super(message, cause);
	}

}