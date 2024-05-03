package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        return true;
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public boolean deleteEmployeeByUsername(String username) {
        return employeeRepository.deleteEmployeeByUsername(username);
    }

    public boolean deleteEmployeeByIdAndUsername(Long id, String username) {
        return employeeRepository.deleteEmployeeByUserIdAndUsername(id, username);
    }

    public Employee retrieveEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> retrieveAllEmployeesInRestaurant(Long restaurantId) {
        return employeeRepository.retrieveAllEmployeesInRestaurant(restaurantId);
    }

    public List<Employee> retrieveAllEmployees() {
        return employeeRepository.findAll();
    }
}
