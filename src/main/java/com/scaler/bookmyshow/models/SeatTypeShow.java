package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "seat_type_shows")
public class SeatTypeShow extends BaseModel
{
    @Enumerated(value = EnumType.ORDINAL)
    private SeatType seatType;

    @ManyToOne
    private Show show;
    private double price;
}
