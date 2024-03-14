package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Payment;
import com.restorun.backendapplication.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    // Autowire might be unnecessary.
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/retrievePaymentById")
    public ResponseEntity<Payment> retrievePaymentById(@RequestBody Long id) {
        Optional<Payment> payment = paymentService.retrievePaymentById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/savePayment")
    public ResponseEntity<String> savePayment(@RequestBody String payment) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Payment paymentObj = mapper.readValue(payment, Payment.class);
        boolean saved = paymentService.savePayment(paymentObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Payment saved successfully");
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<String> deletePayment(@RequestBody Long id) {
        Optional<Payment> payment = paymentService.retrievePaymentById(id);
        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = paymentService.deletePayment(payment);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Payment deleted successfully");

    }


    // return all payments in the system
    @GetMapping("/retrieveAllPayments")
    public ResponseEntity<List<Payment>> retrieveAllPayments() {
        List<Payment> payments = paymentService.retrieveAllPayments();
        if (payments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payments);
    }
}
