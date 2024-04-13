package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Employee;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Employee findByUsername(String username);
    Employee findByUsernameAndPassword(String username, String password);
}
