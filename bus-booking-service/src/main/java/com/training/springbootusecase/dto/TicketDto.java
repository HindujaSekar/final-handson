package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TicketDto {
    private PassengerDetailsDto passengerDetails;
    private String startingPlace;
    private String destination;
    private double fee;
    private LocalDate travelDate;
}