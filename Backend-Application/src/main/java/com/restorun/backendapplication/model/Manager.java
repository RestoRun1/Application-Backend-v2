package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Manager extends Employee { // Assuming Manager extends from Employee

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;*/

    public Manager() {
        super();
        this.role = Role.MANAGER;
    }


    // Constructors, getters, and setters are managed by Lombok annotations
}
