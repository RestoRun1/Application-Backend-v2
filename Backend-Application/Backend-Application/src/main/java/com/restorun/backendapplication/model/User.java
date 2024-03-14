package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.Role;
import jakarta.persistence.*;

@MappedSuperclass
abstract class User {

    // properties of the class

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(nullable = false, unique = true)
    protected String email;

    // role
    @Column(nullable = false)
    protected Role role;


    // default constructor
    public User() {
    }

    // abstract method to be implemented by the subclasses
    public abstract Long getUserId();
    public abstract String getRole();

    public abstract void setRole(String role);

    public abstract String getUsername();

    public abstract void setUsername(String username);

    public abstract String getEmail();

    public abstract void setEmail(String email);


}
