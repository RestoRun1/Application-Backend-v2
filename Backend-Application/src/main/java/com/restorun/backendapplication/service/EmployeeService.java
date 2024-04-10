package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements UserAuthenticationService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee authenticate(String username, String password) {
        // Here you'd add logic to verify the username and password, e.g.:
        return employeeRepository.findByUsernameAndPassword(username, password);
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
