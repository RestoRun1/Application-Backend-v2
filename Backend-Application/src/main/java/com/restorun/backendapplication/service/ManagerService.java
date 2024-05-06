package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.enums.Role;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Manager;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.ManagerRepository;
import com.restorun.backendapplication.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, RestaurantRepository restaurantRepository) {
        this.managerRepository = managerRepository;
        this.restaurantRepository = restaurantRepository;
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
            return new AuthenticatedUser(manager.getUsername(), Collections.singletonList("ROLE_MANAGER"));
        }
        return null;
    }

    public boolean saveManager(Long restaurantId, String username, String email, String password, Role role) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Manager manager = new Manager();
        manager.setRestaurant(restaurant);
        manager.setUsername(username);
        manager.setEmail(email);
        manager.setPassword(password);
        manager.setRole(role);

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
                    existingManager.setPassword(manager.getPassword());
                    existingManager.setUsername(manager.getUsername());
                    existingManager.setRole(manager.getRole());
                    existingManager.setRestaurant(restaurantRepository.findById(manager.getRestaurantId()).orElseThrow(() -> new RuntimeException("Restaurant not found")));
                    managerRepository.save(existingManager);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + manager.getUserId()));
    }

    public List<Manager> retrieveAllManagers() {return managerRepository.findAll();}
}
