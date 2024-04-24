package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);
    Customer findByUsernameAndPassword(String username, String password);
}
