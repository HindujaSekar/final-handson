package com.training.springbootusecase.exceptions;

public class NoArgumentException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoArgumentException(String message) {
		super(message);
	}
}
