package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Customer;
import com.restorun.backendapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return true;
    }

    public boolean saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return true;
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
