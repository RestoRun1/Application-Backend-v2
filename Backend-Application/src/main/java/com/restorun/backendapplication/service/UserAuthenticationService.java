package com.restorun.backendapplication.service;

public interface UserAuthenticationService {
    Object authenticate(String username, String password);
}
