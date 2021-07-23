package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CredentialDto {
	private String email;
	private String password;

}
