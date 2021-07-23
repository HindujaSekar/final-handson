package com.training.springbootusecase.dto;

import com.training.springbootusecase.entity.GenderType;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private GenderType gender;
    private String dateOfBirth;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String firstName, String lastName, String email,
                           String password, GenderType gender, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
