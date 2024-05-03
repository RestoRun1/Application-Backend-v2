package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Meal> retrieveMealById(Long id) {
        return mealRepository.findMealsByRestaurantId(id);
    }

    public boolean saveMeal(Meal meal) {
        mealRepository.save(meal);
        return true;
    }

    public boolean deleteMeal(Optional<Meal> meal) {
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
                    existingMeal.setMenu(meal.getMenu());
                    existingMeal.setName(meal.getName());
                    existingMeal.setPrice(meal.getPrice());
                    mealRepository.save(existingMeal);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + meal.getId()));
    }

    public ArrayList<Meal> findMealsByResId(Long id) {return mealRepository.findMealsByRestaurantId(id);}

    public List<Meal> retrieveAllMeals() {return mealRepository.findAll();}
}
