package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // method for retrieving a customer by email
    /*Customer retrieveCustomerByEmail(String email);*/

    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.password = :password")
    Customer findByUsernamePassword(@Param("username") String email, @Param("password") String password);

}
