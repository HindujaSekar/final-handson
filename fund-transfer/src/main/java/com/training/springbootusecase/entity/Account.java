package com.training.springbootusecase.entity;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Builder
@Data
@ToString
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	private double balance;
	private LocalDate effectiveDate;
	@OneToOne(cascade = CascadeType.ALL)
	private TransactionHistory history;
	public Account() {
		super();
	}
	public Account(long accountId, User user, double balance,
			LocalDate effectiveDate, TransactionHistory history) {
		super();
		this.accountId = accountId;
		this.user = user;
		this.balance = balance;
		this.effectiveDate = effectiveDate;
		this.history = history;
	}
	
	

}
