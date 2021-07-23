package com.training.springbootusecase.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Builder
@Data
@ToString
public class TransactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	private long fromAccountId;
	private long toAccountId;
	private double amountDebited;
	private double balance;
	private LocalDateTime time;
	
	public TransactionHistory(long transactionId, long fromAccountId, long toAccountId, double amountDebited,
			double balance, LocalDateTime time) {
		super();
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amountDebited = amountDebited;
		this.balance = balance;
		this.time = time;
	}

	public TransactionHistory() {
		super();
	}
	

}
