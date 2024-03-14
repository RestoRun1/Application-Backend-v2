package com.restorun.backendapplication.controller;


import com.restorun.backendapplication.model.Waiter;
import com.restorun.backendapplication.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/waiter")

public class WaiterController {
    private final WaiterService waiterService;

    @Autowired
    public WaiterController(WaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @GetMapping("/retrieveWaiterById")
    public ResponseEntity<Waiter> retrieveWaiterById(@RequestBody Long id) {
        Optional<Waiter> waiter = Optional.ofNullable(waiterService.retrieveWaiterById(id));
        if (waiter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(waiter.get());
    }

    @DeleteMapping("/deleteWaiter")
    public ResponseEntity<String> deleteWaiter(@RequestBody Long id) {
        Optional<Waiter> waiter = Optional.ofNullable(waiterService.retrieveWaiterById(id));
        if (waiter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = waiterService.deleteWaiter(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Waiter deleted successfully");
    }

    @PostMapping("/saveWaiter")
    public ResponseEntity<String> saveWaiter(@RequestBody Waiter waiter) {
        boolean saved = waiterService.saveWaiter(waiter);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Waiter saved successfully");
    }
}
