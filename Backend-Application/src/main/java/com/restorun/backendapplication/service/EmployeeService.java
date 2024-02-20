package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public boolean saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        return true;
    }

    public Employee retrieveEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }


}
