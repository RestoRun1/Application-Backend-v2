package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Employee extends User{

    // Employee specific fields

    @Column
    private String password;

    public Employee() {
        super();
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public String getRole() {
        return this.role.toString();
    }

    @Override
    public void setRole(String role) {
        this.role = Role.valueOf(role);
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
