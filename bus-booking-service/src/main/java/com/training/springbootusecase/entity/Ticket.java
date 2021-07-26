package com.training.springbootusecase.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
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
public class Ticket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long ticketId;
    @ManyToOne
    private User userDetails;
    private int noOfTicketsUnderUser;
    private String startingPlace;
    private String destination;
    private double fee;
    private LocalDate travelDate;
    public Ticket() {
    }

    public Ticket(long ticketId, User userDetails,
                  int noOfTicketsUnderUser, String startingPlace, String destination, double fee,
                  LocalDate travelDate) {
        this.ticketId = ticketId;
        this.userDetails = userDetails;
        this.noOfTicketsUnderUser = noOfTicketsUnderUser;
        this.startingPlace = startingPlace;
        this.destination = destination;
        this.fee = fee;
        this.travelDate = travelDate;
    }
}