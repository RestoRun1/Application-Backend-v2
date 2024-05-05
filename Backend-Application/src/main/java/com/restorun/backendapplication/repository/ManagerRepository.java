package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Manager;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByUsername(String username);
    Manager findByUsernameAndPassword(String username, String password);
}