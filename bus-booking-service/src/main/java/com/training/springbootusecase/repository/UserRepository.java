package com.training.springbootusecase.repository;

import com.training.springbootusecase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    boolean existsUserByEmail (String email);
}
