package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MealService {
    private final MealRepository mealRepository;
    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Meal> retrieveMealById(Long id) {
        return mealRepository.findById(id);
    }

    public boolean saveMeal(Meal meal) {
        mealRepository.save(meal);
        return true;
        /*boolean result = updateMeal(meal);
        try {
            if (!result) {
                mealRepository.save(meal);
            }
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }*/
    }

    public boolean deleteMeal(Long id)
    {
        Optional<Meal> meal = mealRepository.findById(id);
        if (meal.isPresent()){
            mealRepository.delete(meal.get());
            return true;
        }
        return true;
    }
    public boolean updateMeal(Meal meal) {
        return mealRepository.findById(meal.getId())
                .map(existingMeal -> {
                    existingMeal.setDescription(meal.getDescription());
                    existingMeal.setName(meal.getName());
                    existingMeal.setPrice(meal.getPrice());
                    existingMeal.setCategory(meal.getCategory());
                    existingMeal.setRating(meal.getRating());
                    mealRepository.save(existingMeal);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + meal.getId()));
    }

    public List<Meal> retrieveAllMeals() {return mealRepository.findAll();}
}
