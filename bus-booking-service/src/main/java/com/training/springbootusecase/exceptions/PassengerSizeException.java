package com.training.springbootusecase.exceptions;

public class PassengerSizeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PassengerSizeException(String message) {
		super(message);
	}
}
