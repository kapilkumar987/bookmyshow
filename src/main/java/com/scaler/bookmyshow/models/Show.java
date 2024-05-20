package com.scaler.bookmyshow.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "shows")
public class Show extends BaseModel
{
    @ManyToOne
    private Screen screen;

    @ManyToOne
    private Movie movie;

    private Date startTime;
    private Date endTime;

    @ElementCollection
    private List<Feature> features;
}
