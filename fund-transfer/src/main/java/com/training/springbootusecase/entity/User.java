package com.training.springbootusecase.entity;

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

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@NotNull(message = "name is required")
	@Size(min = 3, max = 20, message = "name should contain atleast 3 letters")
	private String name;
	@Email(message = "email must be in correct format")
	@NotNull(message = "email is required")
	@Column(unique = true)
	private String email;
	@NotNull(message = "password is required")
	@Size(min = 4, message = "password is too short")
	private String password;
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	public User() {
		super();
	}

	public User(long userId, String name, String email, String password, GenderType gender, 
			AccountType accountType) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.accountType = accountType;
	}

}
