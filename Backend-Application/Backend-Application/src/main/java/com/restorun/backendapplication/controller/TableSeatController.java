package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.TableSeat;
import com.restorun.backendapplication.service.TableSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tableSeat")
public class TableSeatController {
    private final TableSeatService tableSeatService;

    // Autowire might be unnecessary.
    @Autowired
    public TableSeatController(TableSeatService tableSeatService) {
        this.tableSeatService = tableSeatService;
    }

    @GetMapping("/retrieveTableSeatById")
    public ResponseEntity<TableSeat> retrieveTableSeatById(@RequestBody Long id) {
        Optional<TableSeat> tableSeat = tableSeatService.retrieveTableSeatById(id);
        return tableSeat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveTableSeat")
    public ResponseEntity<String> saveTableSeat(@RequestBody String tableSeat) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TableSeat tableSeatObj = mapper.readValue(tableSeat, TableSeat.class);
        boolean saved = tableSeatService.saveTableSeat(tableSeatObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("TableSeat saved successfully");
    }

    @DeleteMapping("/deleteTableSeat")
    public ResponseEntity<String> deleteTableSeat(@RequestBody Long id) {
        Optional<TableSeat> tableSeat = tableSeatService.retrieveTableSeatById(id);
        if (tableSeat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = tableSeatService.deleteTableSeat(tableSeat);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("TableSeat deleted successfully");

    }


    // return all tableSeats in the system
    @GetMapping("/retrieveAllTableSeats")
    public ResponseEntity<List<TableSeat>> retrieveAllTableSeats() {
        List<TableSeat> tableSeats = tableSeatService.retrieveAllTableSeats();
        if (tableSeats.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tableSeats);
    }
}
