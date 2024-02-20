package com.restorun.backendapplication.service;

import com.restorun.backendapplication.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restorun.backendapplication.model.Chef;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    @Autowired
    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    public boolean deleteChef(Long id) {
        chefRepository.deleteById(id);
        return true;
    }

    public boolean saveChef(Chef chef) {
        chefRepository.save(chef);
        return true;
    }

    public Chef retrieveChefById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }
}
