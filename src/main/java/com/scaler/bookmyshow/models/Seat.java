package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "seats")
public class Seat extends BaseModel
{
    private String name;
    private SeatType seatType;
    private int bottomRightX;
    private int bottomRightY;
    private int topLeftX;
    private int topLeftY;

    @ManyToOne
    private Screen screen;
}
