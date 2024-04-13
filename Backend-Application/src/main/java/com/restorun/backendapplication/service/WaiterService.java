package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Waiter;
import com.restorun.backendapplication.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WaiterService implements UserAuthenticationService{
    private final WaiterRepository waiterRepository;
    @Autowired
    public WaiterService(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @Transactional(readOnly = true)
    public Waiter authenticate(String username, String password) {
        // Here you'd add logic to verify the username and password, e.g.:
        return waiterRepository.findByUsernameAndPassword(username, password);
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
