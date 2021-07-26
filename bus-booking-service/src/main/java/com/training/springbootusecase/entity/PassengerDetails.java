package com.training.springbootusecase.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@Entity
@Table
public class PassengerDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long passengerDetailsId;
    private String name;
    private int age;
    @ManyToOne
    private Ticket ticket;

    public PassengerDetails() {
    }

    public PassengerDetails(long passengerDetailsId, String name, int age, Ticket ticket) {
        this.passengerDetailsId = passengerDetailsId;
        this.name = name;
        this.age = age;
        this.ticket = ticket;
    }
}