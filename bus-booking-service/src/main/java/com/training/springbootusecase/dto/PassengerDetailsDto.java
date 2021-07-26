package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PassengerDetailsDto {
    private String name;
    private int age;
}
