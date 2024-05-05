package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.service.DiningTableService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restorun.backendapplication.model.DiningTable;

import java.util.List;

@RestController
@RequestMapping("/api/dining-table")
public class DiningTableController {
    private final DiningTableService diningTableService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DiningTableController(DiningTableService diningTableService, ObjectMapper objectMapper) {
        this.diningTableService = diningTableService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/saveDiningTable")
    @Operation(summary = "Save a dining table", description = "Saves a new dining table or updates an existing dining table based on the provided details")
    public ResponseEntity<String> saveDiningTable(@RequestBody JsonNode diningTableJson) {
        int tableNumber = diningTableJson.get("tableNumber").asInt();
        int seatingCapacity = diningTableJson.get("seatingCapacity").asInt();
        Long restaurantId = diningTableJson.get("restaurantId").asLong();

        diningTableService.saveDiningTable(tableNumber, seatingCapacity, restaurantId);
        return ResponseEntity.ok("{\"message\": \"Dining table saved successfully\"}");
    }

    @DeleteMapping("/deleteDiningTable/{id}")
    public ResponseEntity<String> deleteDiningTable(@PathVariable Long id) {
        boolean isDeleted = diningTableService.deleteDiningTable(id);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\": \"Dining table deleted successfully\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/retrieveAllDiningTables")
    public ResponseEntity<List<DiningTable>> retrieveAllDiningTables() {
        List<DiningTable> diningTables = diningTableService.retrieveAllDiningTables();
        if (diningTables.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diningTables);
    }

    @GetMapping("/retrieveDiningTableById/{id}")
    public ResponseEntity<DiningTable> retrieveDiningTableById(@PathVariable Long id) {
        DiningTable diningTable = diningTableService.retrieveDiningTableById(id).orElse(null);
        if (diningTable == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diningTable);
    }

    @GetMapping("/updateDiningTable")
    public ResponseEntity<String> updateDiningTable(DiningTable diningTable) {
        boolean isUpdated = diningTableService.updateDiningTable(diningTable);
        if (isUpdated) {
            return ResponseEntity.ok("{\"message\": \"Dining table updated successfully\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
