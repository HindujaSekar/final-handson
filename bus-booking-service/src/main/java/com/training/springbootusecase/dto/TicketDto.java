package com.training.springbootusecase.dto;

import com.training.springbootusecase.entity.PassengerDetails;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class TicketDto {
    private List<PassengerDetails> passengerDetails;
    private String startingPlace;
    private String destination;
    private double fee;
    private LocalDate travelDate;
}