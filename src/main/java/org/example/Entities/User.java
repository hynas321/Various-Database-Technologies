package org.example.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("USER")
public class User extends Account {

    public User() {}

    public User(String email, String password) {
        super(email, password);
    }
}