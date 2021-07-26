package com.training.springbootusecase.repository;

import com.training.springbootusecase.entity.PassengerDetails;
import com.training.springbootusecase.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDetailsRepository extends JpaRepository<PassengerDetails,Long> {
  List<PassengerDetails> findAllByTicket(Ticket ticket);
}