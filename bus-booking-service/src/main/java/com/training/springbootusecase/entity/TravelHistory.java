package com.training.springbootusecase.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Builder
@Data
@Entity
@Table
public class TravelHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long historyId;
    private String startingPlace;
    private String destination;
    private double fee;
    private LocalDate travelDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private JourneyStatus journeyStatus;
    @ManyToOne
    private BusDetails busDetails;
    private int noOfSeats;
    @ManyToOne
    private User user;

    public TravelHistory() {
    }

    public TravelHistory(long historyId, String startingPlace, String destination, double fee,
                         LocalDate travelDate, PaymentStatus paymentStatus, JourneyStatus journeyStatus,
                         BusDetails busDetails, int noOfSeats, User user) {
        this.historyId = historyId;
        this.startingPlace = startingPlace;
        this.destination = destination;
        this.fee = fee;
        this.travelDate = travelDate;
        this.paymentStatus = paymentStatus;
        this.journeyStatus = journeyStatus;
        this.busDetails = busDetails;
        this.noOfSeats = noOfSeats;
        this.user = user;
    }
}
