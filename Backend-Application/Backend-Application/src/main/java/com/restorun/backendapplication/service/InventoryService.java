package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.model.Inventory;
import com.restorun.backendapplication.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Inventory> retrieveInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public boolean saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return true;
    }

    public boolean deleteInventory(Optional<Inventory> inventory) {
        if (inventory.isPresent()){
            inventoryRepository.delete(inventory.get());
            return true;
        }
        return true;
    }
    public boolean updateInventory(Inventory inventory) {
        return inventoryRepository.findById(inventory.getId())
                .map(existingInventory -> {
                    existingInventory.setItems(inventory.getItems());
                    existingInventory.setLocation(inventory.getLocation());
                    inventoryRepository.save(existingInventory);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventory.getId()));
    }

}
