package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")

public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/retrieveEmployeeById")
    public ResponseEntity<Employee> retrieveEmployeeById(@RequestBody Long id) {
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
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        boolean saved = employeeService.saveEmployee(employee);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Employee saved successfully");
    }

}
