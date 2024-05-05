package com.restorun.backendapplication.dto;

import java.util.List;

public class AuthenticatedUser {
    private String username;
    private List<String> roles;

    public AuthenticatedUser(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}