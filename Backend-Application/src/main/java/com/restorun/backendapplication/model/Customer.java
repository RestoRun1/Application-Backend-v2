package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.PaymentStatus;
import com.restorun.backendapplication.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Customer extends User{

    // customer specific fields

    @Column
    private String password;

    @Column
    private String phoneNumber;
    // Implement payment methods

    public Customer() {
        super();
        this.role = Role.CUSTOMER;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
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


    // Customer specific getters and setters
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Customer specific methods will be implemented to the service class


    // Implement toString and toJson methods
    @Override
    public String toString() {
        return "Customer{" +
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
