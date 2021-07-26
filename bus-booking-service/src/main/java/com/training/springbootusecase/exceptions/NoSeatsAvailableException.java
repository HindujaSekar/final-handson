package com.training.springbootusecase.exceptions;

public class NoSeatsAvailableException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoSeatsAvailableException(String message) {
		super(message);
	}
}
