package com.restorun.backendapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.restorun.backendapplication.model.User;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ChefService chefService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private WaiterService waiterService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        // @TODO
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                "", // Since we do not handle passwords here
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }

    private User findUserByUsername(String username) {
        // Try to find the user using each service
        User user = adminService.findByUsername(username);
        if (user != null) return user;
        user = chefService.findByUsername(username);
        if (user != null) return user;
        user = customerService.findByUsername(username);
        if (user != null) return user;
        user = employeeService.findByUsername(username);
        if (user != null) return user;
        user = managerService.findByUsername(username);
        if (user != null) return user;
        user = waiterService.findByUsername(username);
        if (user != null) return user;

        return null; // User not found in any service
    }
}
