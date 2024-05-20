package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "show_seats")
public class ShowSeat extends BaseModel
{
    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @Enumerated(value = EnumType.ORDINAL)
    private SeatStatus seatStatus;

    @ManyToOne
    private User bookedBy; // In Show many seats booked by User
}