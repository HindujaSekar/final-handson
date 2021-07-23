package com.training.springbootusecase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.springbootusecase.entity.Account;
import com.training.springbootusecase.entity.User;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findByUser(User user);


}
