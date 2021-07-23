package com.training.springbootusecase.exceptions;

public class FundTransferDownException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FundTransferDownException(String message) {
		super(message);
	}
}
