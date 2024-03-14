package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Kitchen;
import com.restorun.backendapplication.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {
    private final KitchenService kitchenService;

    // Autowire might be unnecessary.
    @Autowired
    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/retrieveKitchenById")
    public ResponseEntity<Kitchen> retrieveKitchenById(@RequestBody Long id) {
        Optional<Kitchen> kitchen = kitchenService.retrieveKitchenById(id);
        return kitchen.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveKitchen")
    public ResponseEntity<String> saveKitchen(@RequestBody String kitchen) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Kitchen kitchenObj = mapper.readValue(kitchen, Kitchen.class);

        boolean saved = kitchenService.saveKitchen(kitchenObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Kitchen saved successfully");
    }

    @DeleteMapping("/deleteKitchen")
    public ResponseEntity<String> deleteKitchen(@RequestBody Long id) {
        Optional<Kitchen> kitchen = kitchenService.retrieveKitchenById(id);
        if (kitchen.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = kitchenService.deleteKitchen(kitchen);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Kitchen deleted successfully");

    }
    // @TODO, A RESTAURANT CAN HAVE MULTIPLE KITCHENS?
    /*
    @GetMapping("/retrieveAllKitchens")
    public ResponseEntity<List<Kitchen>> retrieveAllKitchens() {

        List<Kitchen> kitchens = kitchenService.retrieveAllKitchens();
        if (kitchens.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(kitchens);
    }
    */
}
