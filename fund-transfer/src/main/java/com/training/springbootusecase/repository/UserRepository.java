package com.training.springbootusecase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.springbootusecase.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	boolean existsUserByEmail (String email);

}
