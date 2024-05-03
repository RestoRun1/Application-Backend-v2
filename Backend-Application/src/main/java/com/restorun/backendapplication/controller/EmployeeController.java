package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")

public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/retrieveAllEmployeesByRestaurantId/{restaurantId}")
    public ResponseEntity<List<Employee>> retrieveAllEmployeesByRestaurantId(@PathVariable Long restaurantId) {
        List<Employee> employees = employeeService.retrieveAllEmployeesInRestaurant(restaurantId);
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/retrieveEmployeeById/{id}")
    public ResponseEntity<Employee> retrieveEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.retrieveEmployeeById(id));
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestBody Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.retrieveEmployeeById(id));
        if (employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = employeeService.deleteEmployee(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PostMapping("/saveEmployee")
    @Operation(summary = "Save an employee", description = "Saves a new employee or updates an existing employee based on the provided details")
    public ResponseEntity<String> saveEmployee(@RequestBody JsonNode employeeJson) {
        Employee employee;
        try {
            employee = objectMapper.treeToValue(employeeJson, Employee.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }
        boolean isSaved = employeeService.saveEmployee(employee);
        if (isSaved) {
            return ResponseEntity.ok("{\"message\": \"Employee saved successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save employee\"}");
        }
    }

    @DeleteMapping("/deleteEmployeeByUsername")
    public ResponseEntity<String> deleteEmployeeByUsername(@RequestBody String username) {
        boolean deleted = employeeService.deleteEmployeeByUsername(username);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @DeleteMapping("/deleteEmployeeByIdAndUsername")
    public ResponseEntity<String> deleteEmployeeByIdAndUsername(@RequestBody Long id, @RequestBody String username) {
        boolean deleted = employeeService.deleteEmployeeByIdAndUsername(id, username);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
