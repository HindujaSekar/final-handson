package com.training.springbootusecase.dto;

import com.training.springbootusecase.entity.JourneyStatus;
import com.training.springbootusecase.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class HistoryDto {
    private String startingPlace;
    private String destination;
    private double fee;
    private LocalDate travelDate;
    private PaymentStatus paymentStatus;
    private JourneyStatus journeyStatus;
    private int noOfSeats;
    private String busName;
    private String userDetails;
}