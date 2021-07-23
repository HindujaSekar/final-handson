package com.training.springbootusecase.dto;


import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RequestDto {
	@NotNull(message ="Account Id is needed")
	private long accountId;
	@NotNull(message ="Beneficiary Account Id is needed")
	private long beneficiaryPersonAccountId;
	private String beneficiaryUserName;

}
