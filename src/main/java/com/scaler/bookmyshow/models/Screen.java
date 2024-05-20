package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false) // This annotation from Lombok generates equals() and hashCode() methods for the class, excluding fields inherited from its superclass (if any).
@Entity(name = "screens")
public class Screen extends BaseModel
{
    private String name;

    // This annotation establishes a one-to-many relationship between the Screen entity and the Seat entity.
    // It indicates that one screen can have multiple seats. The mappedBy attribute specifies the field in the Seat entity that owns the relationship.
    @OneToMany(mappedBy = "screen", fetch = FetchType.EAGER)
    private List<Seat> seats;

    private ScreenStatus status;

    @ElementCollection
    private List<Feature> features;

    @ManyToOne // In screens table there is theatre_id which is FK and PK on theatres table
    private Theatre theatre;

    // This annotation establishes a many-to-one relationship between the Screen entity and the Theatre entity.
    // It indicates that many screens belong to one theatre.
}
