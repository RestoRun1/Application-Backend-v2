package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.CustomerDTO;
import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerService implements UserAuthenticationService{

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Customer customer = customerRepository.findByUsernameAndPassword(username, password);
        if (customer != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(customer.getUsername(), Collections.singletonList("ROLE_CUSTOMER"));
        }
        return null;
    }

    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return true;  // Successfully deleted
        }
        return false;  // No restaurant found to delete
    }

    public List<Customer> retrieveAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return true;
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Customer retrieveCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer retrieveByUsernamePassword(String username, String password){
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    public Customer findOrCreateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getUserId()).orElse(null);
        if (customer == null) {
            customer = new Customer();
            customer.setUsername(customerDTO.getUsername());
            customer.setEmail(customerDTO.getEmail());
            customer.setPassword(customerDTO.getPassword()); // Make sure to encrypt this in production
            customer.setRole(customerDTO.getRole()); // Assuming Role is an enum
            customerRepository.save(customer);
        }
        return customer;
    }
    /*public Customer retrieveCustomerByEmail(String email) {
        return customerRepository.retrieveCustomerByEmail(email);
    }*/

    // planned business logic
    /*
    make reservation
    cancel reservation
    place order
    favourite restaurant
    get restaurant nearby
    view profile
    edit profile
    manage payment methods
     */

}
