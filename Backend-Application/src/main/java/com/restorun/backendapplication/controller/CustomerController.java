package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")

public class CustomerController {
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("retrieveCustomerById")
    public ResponseEntity<Customer> retrieveCustomerById(@RequestBody Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerService.retrieveCustomerById(id));
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("saveCustomer")
    @Operation(summary = "Save a customer", description = "Saves a new customer or updates an existing customer based on the provided details")
    public ResponseEntity<String> saveCustomer(@RequestBody JsonNode customerJson) {
        Customer customer;
        try {
            customer = objectMapper.treeToValue(customerJson, Customer.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }
        boolean isSaved = customerService.saveCustomer(customer);
        if (isSaved) {
            return ResponseEntity.ok("{\"message\": \"Customer saved successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save customer\"}");
        }
    }

    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\": \"Customer deleted successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to delete customer\"}");
        }
    }

    @GetMapping("/retrieveAllCustomers")
    public ResponseEntity<List<Customer>> retrieveAllCustomers() {
        List<Customer> customers = customerService.retrieveAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customers);
    }

    /*@GetMapping("retrieveCustomerByEmail")
    public ResponseEntity<Customer> retrieveCustomerByEmail(@RequestBody JsonNode email) {
        Customer customer = customerService.retrieveCustomerByEmail(email.asText());
        return ResponseEntity.ok(customer);
    }*/
}