package com.restorun.backendapplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Disable CSRF protection as it's not needed for stateless APIs typically
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()  // Permit all Swagger-related paths
                        .anyRequest().authenticated())  // All other requests require authentication
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // Use stateless session; session won't be used to store user's state.

        return http.build();
    }
}
