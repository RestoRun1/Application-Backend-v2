package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.service.BillService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;

    // Autowire might be unnecessary.
    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/retrieveBillById")
    public ResponseEntity<Bill> retrieveBillById(@RequestBody Long id) {

        Optional<Bill> bill = billService.retrieveBillById(id);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveBill")
    public ResponseEntity<String> saveBill(@RequestBody String bill) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Bill billObj = mapper.readValue(bill, Bill.class);

        boolean saved = billService.saveBill(billObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Bill " + billObj.toJSON() + " saved successfully");
    }

    @DeleteMapping("/deleteBill")
    public ResponseEntity<String> deleteBill(@RequestBody Long id) {

        Optional<Bill> bill = billService.retrieveBillById(id);
        if (bill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = billService.deleteBill(bill);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Bill deleted successfully");

    }


    // return all bills in the system
    @GetMapping("/retrieveAllBills")
    public ResponseEntity<List<Bill>> retrieveAllBills() {

        List<Bill> bills = billService.retrieveAllBills();
        if (bills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bills);

    }
}
