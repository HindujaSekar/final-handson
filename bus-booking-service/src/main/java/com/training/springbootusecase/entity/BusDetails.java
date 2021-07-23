package com.training.springbootusecase.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@RequiredArgsConstructor
@Data
@Entity
@Table
public class BusDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long busId;
    private String busName;
    private String source;
    private String destination;
    private LocalTime startingTime;
    private LocalTime reachingTime;
    private double ticketPrice;
    private long contact;
    private int totalSeats;
    private int noOfAvailableSeats;
    @Enumerated(EnumType.STRING)
    private BusType busType;
    private double customerReview;


}
