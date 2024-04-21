package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;

public interface UserAuthenticationService {
    AuthenticatedUser authenticate(String username, String password);
}
