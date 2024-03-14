package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.MealItem;
import com.restorun.backendapplication.repository.MealItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MealItemService {
    private final MealItemRepository mealItemRepository;
    @Autowired
    public MealItemService(MealItemRepository mealItemRepository) {
        this.mealItemRepository = mealItemRepository;
    }

    @Transactional(readOnly = true)
    public Optional<MealItem> retrieveMealItemById(Long id) {
        return mealItemRepository.findById(id);
    }

    public boolean saveMealItem(MealItem mealItem) {
        mealItemRepository.save(mealItem);
        return true;
    }

    public boolean deleteMealItem(Optional<MealItem> mealItem) {
        if (mealItem.isPresent()){
            mealItemRepository.delete(mealItem.get());
            return true;
        }
        return true;
    }
    public boolean updateMealItem(MealItem mealItem) {
        return mealItemRepository.findById(mealItem.getId())
                .map(existingMealItem -> {
                    existingMealItem.setDescription(mealItem.getDescription());
                    existingMealItem.setName(mealItem.getName());
                    existingMealItem.setPrice(mealItem.getPrice());
                    mealItemRepository.save(existingMealItem);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("MealItem not found with id: " + mealItem.getId()));
    }

    public List<MealItem> retrieveAllMealItems(){ return mealItemRepository.findAll();}
}
