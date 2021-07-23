package com.training.springbootusecase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long userId;
    @NotNull(message = "first name is required")
    @Size(min = 3, max = 50, message = "name should contain atleast 3 letters")
    private String firstName;
    private String lastName;
    @Email(message = "email must be in correct format")
    @NotNull(message = "email is required")
    @Column(unique = true)
    private String email;
    @NotNull(message = "password is required")
    @Size(min = 4, message = "password is too short")
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @NotNull(message = "date of birth is required")
    private LocalDate dateOfBirth;

    public User() {
    }

    public User(long userId, String firstName, String lastName, String email,
                String password, GenderType gender, LocalDate dateOfBirth) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;

    }
}
