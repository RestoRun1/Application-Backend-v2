package com.restorun.backendapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restorun.backendapplication.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
public class Employee extends User{

    // Employee specific fields
    @Getter
    @Setter
    @Column
    private String password;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Employee() {
        super();
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    // Implement toString and toJson methods
    @Override
    public String toString() {
        return "Employee{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"userId\":" + userId +
                ", \"username\":\"" + username + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"role\":\"" + role + '\"' +
                ", \"password\":\"" + password + '\"' +
                '}';
    }

    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurant.getId();
    }
}
