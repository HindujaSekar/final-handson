package com.training.springbootusecase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.springbootusecase.entity.BeneficiaryConnections;

public interface ConnectionsRepository extends JpaRepository<BeneficiaryConnections, Long>{

}
