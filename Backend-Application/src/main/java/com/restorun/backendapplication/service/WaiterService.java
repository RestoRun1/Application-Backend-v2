package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Waiter;
import com.restorun.backendapplication.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class WaiterService implements UserAuthenticationService{
    private final WaiterRepository waiterRepository;
    @Autowired
    public WaiterService(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Waiter waiter = waiterRepository.findByUsernameAndPassword(username, password);
        if (waiter != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(waiter.getUsername(), Collections.singletonList("ROLE_ADMIN"));
        }
        return null;
    }

    public boolean deleteWaiter(Long id) {
        waiterRepository.deleteById(id);
        return true;
    }

    public boolean saveWaiter(Waiter waiter) {
        waiterRepository.save(waiter);
        return true;
    }

    public Waiter findByUsername(String username) {
        return waiterRepository.findByUsername(username);
    }

    public Waiter retrieveWaiterById(Long id) {
        return waiterRepository.findById(id).orElse(null);
    }


}
