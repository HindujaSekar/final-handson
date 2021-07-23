package com.training.springbootusecase.repository;

import com.training.springbootusecase.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByTravelDateBetween(LocalDate startDate, LocalDate endDate);
}
