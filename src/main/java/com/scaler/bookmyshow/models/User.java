package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "users")
public class User extends BaseModel
{
    private String name;
    private String email;
    private String password;

    private UserType userType;
}
