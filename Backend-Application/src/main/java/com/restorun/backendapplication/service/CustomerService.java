package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
            return new AuthenticatedUser(customer.getUsername(), Collections.singletonList("ROLE_ADMIN"));
        }
        return null;
    }

    public boolean deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return true;
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
