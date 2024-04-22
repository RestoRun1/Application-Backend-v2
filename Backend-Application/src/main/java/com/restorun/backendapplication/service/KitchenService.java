/*
package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Inventory;
import com.restorun.backendapplication.model.Kitchen;
import com.restorun.backendapplication.repository.InventoryRepository;
import com.restorun.backendapplication.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    @Autowired
    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Kitchen> retrieveKitchenById(Long id) {
        return kitchenRepository.findById(id);
    }

    public boolean saveKitchen(Kitchen kitchen) {
        kitchenRepository.save(kitchen);
        return true;
    }

    public boolean deleteKitchen(Optional<Kitchen> kitchen) {
        if (kitchen.isPresent()){
            kitchenRepository.delete(kitchen.get());
            return true;
        }
        return true;
    }
    public boolean updateKitchen(Kitchen kitchen) {
        return kitchenRepository.findById(kitchen.getId())
                .map(existingKitchen -> {
                    existingKitchen.setName(kitchen.getName());
                    existingKitchen.setOrders(kitchen.getOrders());
                    kitchenRepository.save(existingKitchen);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Kitchen not found with id: " + kitchen.getId()));
    }
}
*/
