package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterResponseDto {
    private String message;
    private String firstName;
    private String email;
}
