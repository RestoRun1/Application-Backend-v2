package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Waiter;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface WaiterRepository extends JpaRepository<Waiter, Long> {
    Waiter findByUsername(String username);
    Waiter findByUsernameAndPassword(String username, String password);
}
