package com.restorun.backendapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF as it's not typically needed for API security
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger UI
                        .requestMatchers("/api/auth/**").permitAll() // Login endpoint
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Admin only endpoints
                        .requestMatchers("/waiter/**").hasAuthority("ROLE_WAITER") // Waiter only endpoints
                        .requestMatchers("/chef/**").hasAuthority("ROLE_CHEF") // Chef only endpoints
                        .requestMatchers("/customer/**").hasAuthority("ROLE_CUSTOMER") // Customer only endpoints
                        .requestMatchers("/employee/**").hasAuthority("ROLE_EMPLOYEE") // Employee only endpoints
                        .requestMatchers("/manager/**").hasAuthority("ROLE_MANAGER") // Manager only endpoints
                        .anyRequest().authenticated()) // Other requests
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be used
                .and()
                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); // JWT filter

        return http.build();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(); // Ensure this bean is correctly implemented
    }
}
