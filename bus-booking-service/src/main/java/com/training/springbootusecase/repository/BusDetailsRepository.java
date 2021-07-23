package com.training.springbootusecase.repository;

import com.training.springbootusecase.entity.BusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusDetailsRepository extends JpaRepository<BusDetails,Long> {

    List<BusDetails> findBySourceAndDestination(String source,String destination);

}
