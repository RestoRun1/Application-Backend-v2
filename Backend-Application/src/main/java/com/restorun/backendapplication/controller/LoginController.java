package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.dto.LoginRequest;
import com.restorun.backendapplication.service.*;
import com.restorun.backendapplication.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;
import java.util.Objects;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired private AdminService adminService;
    @Autowired private ChefService chefService;
    @Autowired private CustomerService customerService;
    @Autowired private EmployeeService employeeService;
    @Autowired private ManagerService managerService;
    @Autowired private WaiterService waiterService;
    @Autowired private JwtUtil jwtUtil;  // Inject JwtUtil

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Object user = Stream.of(adminService, chefService, customerService, employeeService, managerService, waiterService)
                .map(service -> service.authenticate(request.getUsername(), request.getPassword()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT token
        final String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok().body(new AuthResponse(token));
    }

    // Inner class to encapsulate the authentication response
    static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
