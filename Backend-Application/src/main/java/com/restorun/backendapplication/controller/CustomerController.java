package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.service.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@Api(tags = "Customer Controller")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("retrieveCustomerById")
    public ResponseEntity<Customer> retrieveCustomerById(@RequestBody Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerService.retrieveCustomerById(id));
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer.get());
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
