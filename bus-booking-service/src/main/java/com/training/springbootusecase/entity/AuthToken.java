package com.training.springbootusecase.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthToken {

    private String token;
    private String email;
}
