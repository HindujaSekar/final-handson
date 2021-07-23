package com.training.springbootusecase.repository;

import com.training.springbootusecase.entity.TravelHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TravelHistoryRepository extends JpaRepository<TravelHistory,Long> {
    List<TravelHistory> findByTravelDateBetween(LocalDate fromDate, LocalDate toDate);
}
