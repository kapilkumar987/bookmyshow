package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "tickets")
public class Ticket extends BaseModel
{
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Show show;

    @OneToMany
    private List<Seat> seats;

    @ManyToOne
    private User user;

    private double price;
    private Date timeOfBooking;
    private PaymentStatus paymentStatus;
}
