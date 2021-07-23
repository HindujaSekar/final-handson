package com.training.springbootusecase.exceptions;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FieldErrorDto {
	private int errorCode;
	private String errorMessage;
	private List<String> errors;

}
