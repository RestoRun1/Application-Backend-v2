package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")

public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("retrieveCustomerById")
    public ResponseEntity<Customer> retrieveCustomerById(@RequestBody Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerService.retrieveCustomerById(id));
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestBody Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerService.retrieveCustomerById(id));
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = customerService.deleteCustomer(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PostMapping("saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        boolean saved = customerService.saveCustomer(customer);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Customer saved successfully");
    }

    // planned business logic?

}
