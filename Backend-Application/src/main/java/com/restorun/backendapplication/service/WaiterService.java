package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Waiter;
import com.restorun.backendapplication.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaiterService {

    private final WaiterRepository waiterRepository;

    @Autowired
    public WaiterService(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    public boolean deleteWaiter(Long id) {
        waiterRepository.deleteById(id);
        return true;
    }

    public boolean saveWaiter(Waiter waiter) {
        waiterRepository.save(waiter);
        return true;
    }

    public Waiter retrieveWaiterById(Long id) {
        return waiterRepository.findById(id).orElse(null);
    }


}
