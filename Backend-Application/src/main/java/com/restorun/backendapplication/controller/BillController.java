package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.service.BillService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
@Api(tags = "Bill Controller")
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
        if (bill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bill.get());

    }

    /*
    @PostMapping("/saveBill")
    public ResponseEntity<String> saveBill(@RequestBody Bill billRequest) {
        Bill bill = new Bill(billRequest.getTotalAmount(), billRequest.getTip(), billRequest.getTax(), PaymentStatus.valueOf(billRequest.getStatus()));
        boolean saved = billService.saveBill(bill);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Bill saved successfully");
    }

     */

    @GetMapping("/deleteBill")
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
