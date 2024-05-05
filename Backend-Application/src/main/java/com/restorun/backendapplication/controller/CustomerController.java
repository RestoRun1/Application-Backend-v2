package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.enums.Role;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;

//import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

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

    // @PostMapping("saveCustomer")
    // @Operation(summary = "Save a customer", description = "Saves a new customer or updates an existing customer based on the provided details")
    // public ResponseEntity<String> saveCustomer(@RequestBody JsonNode customerJson) {
    //     Customer customer;
    //     try {
    //         customer = objectMapper.treeToValue(customerJson, Customer.class);
    //     } catch (JsonProcessingException e) {
    //         return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
    //     }
    //     boolean isSaved = customerService.saveCustomer(customer);
    //     if (isSaved) {
    //         return ResponseEntity.ok("{\"message\": \"Customer saved successfully\"}");
    //     } else {
    //         return ResponseEntity.badRequest().body("{\"error\": \"Failed to save customer\"}");
    //     }
    // }

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

    @PostMapping("saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody Map<String, Object> customerBody ) {

        String username = (String) customerBody.get("username");
        String email = (String) customerBody.get("email");
        String password = (String) customerBody.get("password"); 
        String confirmedPassword = (String) customerBody.get("confirmedPassword");
        String phoneNumber = (String) customerBody.get("phoneNumber");

        if(username.length() == 0){
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }

        if(!password.equals(confirmedPassword)){
            return ResponseEntity.badRequest().body("password did not matched");
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber(phoneNumber);
        
        boolean saved = customerService.saveCustomer(customer);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println("Customer is" + customer.toString());

        return ResponseEntity.ok("Customer saved successfully");
    }

    @GetMapping("login")
    public ResponseEntity<Customer> loginCustomer(
        @RequestParam String username,
        @RequestParam String password
    ){

        Customer customer = customerService.retrieveByUsernamePassword(username, password);
        
        if(customer == null){
            return ResponseEntity.badRequest().body(null);
        }

        else{
            System.out.println(customer.toString());
        }
        
        return ResponseEntity.ok(customer);
    }


    /*@GetMapping("retrieveCustomerByEmail")
    public ResponseEntity<Customer> retrieveCustomerByEmail(@RequestBody JsonNode email) {
        Customer customer = customerService.retrieveCustomerByEmail(email.asText());
        return ResponseEntity.ok(customer);
    }*/
}