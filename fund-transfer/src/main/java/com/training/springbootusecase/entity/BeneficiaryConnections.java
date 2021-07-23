package com.training.springbootusecase.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Builder
@Data
@ToString
public class BeneficiaryConnections {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long beneficiaryId;
	@ManyToOne
	@JoinColumn
	private Account account;
	private String beneficiaryUserName;
	private long beneficiaryPersonAccountId;
	public BeneficiaryConnections() {
		super();
	}
	public BeneficiaryConnections(long beneficiaryId, Account account, String beneficiaryUserName,
			long beneficiaryPersonAccountId) {
		super();
		this.beneficiaryId = beneficiaryId;
		this.account = account;
		this.beneficiaryUserName = beneficiaryUserName;
		this.beneficiaryPersonAccountId = beneficiaryPersonAccountId;
	}
	

}
