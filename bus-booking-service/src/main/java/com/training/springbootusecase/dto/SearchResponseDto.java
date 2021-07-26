package com.training.springbootusecase.dto;

import com.training.springbootusecase.entity.BusType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class SearchResponseDto {
    private String busName;
    private String source;
    private String destination;
    private LocalTime startingTime;
    private LocalTime reachingTime;
    private double ticketPrice;
    private BusType busType;
    private double customerReview;
    private int noOfAvailableSeats;
}