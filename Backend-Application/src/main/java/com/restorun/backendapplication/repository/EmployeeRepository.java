package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Employee;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean deleteEmployeeByUserIdAndUsername(Long userId, String username);
    boolean deleteEmployeeByUsername(String username);
    @Query("SELECT e FROM Employee e WHERE e.restaurant.id = :restaurantId")
    List<Employee> retrieveAllEmployeesInRestaurant(Long restaurantId);
}