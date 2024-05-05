package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.dto.LoginRequest;
import com.restorun.backendapplication.security.JwtUtil;
import com.restorun.backendapplication.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired private AdminService adminService;
    @Autowired private ChefService chefService;
    @Autowired private CustomerService customerService;
    @Autowired private EmployeeService employeeService;
    @Autowired private ManagerService managerService;
    @Autowired private WaiterService waiterService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt for: " + request.getUsername());

        AuthenticatedUser user = Stream.of(adminService, chefService, customerService, managerService, waiterService)
                .map(service -> {
                    AuthenticatedUser au = service.authenticate(request.getUsername(), request.getPassword());
                    if (au != null) {
                        System.out.println("Authenticated user: " + au.getUsername() + " with roles: " + au.getRoles());
                    }
                    return au;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        System.out.println("Generating token for: " + user.getUsername() + " with roles: " + user.getRoles());
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        System.out.println("Generated token: " + token);
        return ResponseEntity.ok(new AuthResponse(token, user.getRoles()));
    }
    static class AuthResponse {
        private String token;
        private List<String> roles;

        public AuthResponse(String token, List<String> roles) {
            this.token = token;
            this.roles = roles;
        }

        public String getToken() {
            return token;
        }

        public List<String> getRoles() {
            return roles;
        }
    }
}
