package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal")
public class MealController {
    private final MealService mealService;

    // Autowire might be unnecessary.
    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/retrieveMealById")
    public ResponseEntity<List<Meal>> retrieveMealById(@RequestParam Long resId) {

        System.out.println("ID IS ->   " + resId);

        List<Meal> meal = mealService.retrieveMealById(resId);
        System.out.println(meal.toString());
        
        return ResponseEntity.ok(meal);
    }

    @PostMapping("/saveMeal")
    public ResponseEntity<String> saveMeal(@RequestBody String meal) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Meal mealObj = mapper.readValue(meal, Meal.class);
        boolean saved = mealService.saveMeal(mealObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Meal saved successfully");
    }

    @DeleteMapping("/deleteMeal")
    public ResponseEntity<String> deleteMeal(@RequestBody Long id) {
        Optional<Meal> meal = mealService.retrieveMealById(id);
        if (meal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = mealService.deleteMeal(meal);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Meal deleted successfully");

    }


    // return all meals in the system
    @GetMapping("/retrieveAllMeals")
    public ResponseEntity<List<Meal>> retrieveAllMeals() {
        List<Meal> meals = mealService.retrieveAllMeals();
        if (meals.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meals);
    }
}
