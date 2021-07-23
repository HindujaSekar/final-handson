package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BookBusDto {

    private String busName;
    private List<PassengerDetails> passengerDetails;
    private int noOfSeats;

    public BookBusDto() {
    }

    public BookBusDto(String busName, List<PassengerDetails> passengerDetails, int noOfSeats) {
        this.busName = busName;
        this.passengerDetails = passengerDetails;
        this.noOfSeats = noOfSeats;
    }
}
