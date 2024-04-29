package com.restorun.backendapplication.service;
import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restorun.backendapplication.model.Chef;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Transactional
@Service
public class ChefService implements UserAuthenticationService{

    private final ChefRepository chefRepository;
    @Autowired
    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }


    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Chef chef = chefRepository.findByUsernameAndPassword(username, password);
        if (chef != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(chef.getUsername(), Collections.singletonList("ROLE_CHEF"));
        }
        return null;
    }

    public List<Chef> retrieveAllChefs() {
        return chefRepository.findAll();
    }
    public boolean deleteChef(Long id) {
        chefRepository.deleteById(id);
        return true;
    }

    public boolean saveChef(Chef chef) {
        chefRepository.save(chef);
        return true;
    }

    public boolean updateChef(Chef chef) {
        return chefRepository.findById(chef.getUserId())
                .map(existingChef -> {
                    existingChef.setEmail(chef.getEmail());
                    existingChef.setUsername(chef.getUsername());
                    existingChef.setRestaurant(chef.getRestaurant());
                    chefRepository.save(existingChef);

                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Chef not found with id: " + chef.getUserId()));
    }

    public Chef retrieveChefById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    public Chef findByUsername(String username) {
        return chefRepository.findByUsername(username);
    }
}
