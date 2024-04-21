package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Manager;
import com.restorun.backendapplication.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService implements UserAuthenticationService{
    private final ManagerRepository managerRepository;
    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Transactional(readOnly = true)
    public Manager retrieveManagerById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Manager manager = managerRepository.findByUsernameAndPassword(username, password);
        if (manager != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(manager.getUsername(), Collections.singletonList("ROLE_ADMIN"));
        }
        return null;
    }

    public boolean saveManager(Manager manager) {
        managerRepository.save(manager);
        return true;
    }

    public Manager findByUsername(String username) {
        return managerRepository.findByUsername(username);
    }

    public boolean deleteManager(Long managerId) {
        if (managerRepository.existsById(managerId)) {
            managerRepository.deleteById(managerId);
            return true; // Admin found and deleted successfully
        } else {
            //throw new RuntimeException("Admin not found with id: " + adminId);
            return false;
        }
    }
    public boolean updateManager(Manager manager) {
        return managerRepository.findById(manager.getUserId())
                .map(existingManager -> {
                    existingManager.setEmail(manager.getEmail());
                    existingManager.setRestaurant(manager.getRestaurant());
                    existingManager.setUsername(manager.getUsername());
                    existingManager.setRole(manager.getRole());
                    managerRepository.save(existingManager);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + manager.getUserId()));
    }

    public List<Manager> retrieveAllManagers() {return managerRepository.findAll();}
}
