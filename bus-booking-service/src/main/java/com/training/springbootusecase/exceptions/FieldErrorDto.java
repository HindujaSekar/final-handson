package com.training.springbootusecase.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FieldErrorDto {
	private int errorCode;
	private String errorMessage;
	private List<String> errors;

}
