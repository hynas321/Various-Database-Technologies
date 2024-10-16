package org.example.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Account {

    public Admin() {}

    public Admin(String email, String password) {
        super(email, password);
    }
}