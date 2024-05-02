package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Employee;
import com.restorun.backendapplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class EmployeeService implements UserAuthenticationService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Employee employee = employeeRepository.findByUsernameAndPassword(username, password);
        if (employee != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(employee.getUsername(), Collections.singletonList("ROLE_EMPLOYEE"));
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public boolean saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        return true;
    }

    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public Employee retrieveEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }


}
