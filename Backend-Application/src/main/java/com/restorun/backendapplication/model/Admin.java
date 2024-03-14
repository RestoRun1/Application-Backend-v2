package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
@Hidden
public class Admin extends User {

    // Admin specific fields

    @Column
    private String password;

    public Admin() {
        super();
        this.role = Role.ADMIN;
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

    // Admin specific getters and setters
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Admin specific methods will be implemented to the service class


}
