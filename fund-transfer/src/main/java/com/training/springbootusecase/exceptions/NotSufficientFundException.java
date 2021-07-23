package com.training.springbootusecase.exceptions;

public class NotSufficientFundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotSufficientFundException(String message) {
		super(message);
	}

}
