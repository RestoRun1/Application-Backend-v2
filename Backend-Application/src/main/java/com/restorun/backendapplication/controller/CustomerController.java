package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.enums.Role;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.service.CustomerService;

import io.swagger.v3.core.util.Json;
import jakarta.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ResponseEntity<String> saveCustomer(@RequestBody Map<String, Object> customerBody ) {

        String username = (String) customerBody.get("username");
        String email = (String) customerBody.get("email");
        String password = (String) customerBody.get("password"); 
        String confirmedPassword = (String) customerBody.get("confirmedPassword");

        if(username.length() == 0){
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }

        if(!password.equals(confirmedPassword)){
            return ResponseEntity.badRequest().body("password did not matched");
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(confirmedPassword);
        customer.setRole(Role.CUSTOMER.getRole());
        
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

    



}
