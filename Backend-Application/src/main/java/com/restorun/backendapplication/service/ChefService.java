package com.restorun.backendapplication.service;
import com.restorun.backendapplication.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restorun.backendapplication.model.Chef;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class ChefService {

    private final ChefRepository chefRepository;
    @Autowired
    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
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
}
