package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")

public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/retrieveEmployeeById")
    public ResponseEntity<Employee> retrieveEmployeeById(@RequestBody Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.retrieveEmployeeById(id));
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
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

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/saveEmployee")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        boolean saved = employeeService.saveEmployee(employee);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Employee saved successfully");
    }

    @GetMapping("/retrieveAllEmployeesByRestaurantId/{restaurantId}")
    public ResponseEntity<List<Employee>> retrieveAllEmployeesByRestaurantId(@PathVariable Long restaurantId) {
        List<Employee> employees = employeeService.retrieveAllEmployeesInRestaurant(restaurantId);
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

}
