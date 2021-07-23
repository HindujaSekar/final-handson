package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountInfoDto {
	private String userName;
	private String email;
	private double balance;

}
