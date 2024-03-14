package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Inventory;
import com.restorun.backendapplication.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/retrieveInventoryById")
    public ResponseEntity<Inventory> retrieveInventoryById(@RequestBody Long id) {
        Optional<Inventory> inventory = inventoryService.retrieveInventoryById(id);
        return inventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveInventory")
    public ResponseEntity<String> saveInventory(@RequestBody String inventory) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Inventory inventoryObj = mapper.readValue(inventory, Inventory.class);

        boolean saved = inventoryService.saveInventory(inventoryObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Inventory saved successfully");
    }

    @DeleteMapping("/deleteInventory")
    public ResponseEntity<String> deleteInventory(@RequestBody Long id) {
        Optional<Inventory> inventory = inventoryService.retrieveInventoryById(id);
        if (inventory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = inventoryService.deleteInventory(inventory);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Inventory deleted successfully");

    }
}
