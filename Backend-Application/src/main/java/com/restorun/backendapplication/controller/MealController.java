package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal")
public class MealController {
    private final MealService mealService;
    private final ObjectMapper objectMapper;

    // Autowire might be unnecessary.
    @Autowired
    public MealController(MealService mealService, ObjectMapper objectMapper) {
        this.mealService = mealService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/retrieveMealById")
    public ResponseEntity<Meal> retrieveMealById(@RequestBody Long id) {
        Optional<Meal> meal = mealService.retrieveMealById(id);
        return meal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/saveMeal")
    public ResponseEntity<String> saveMeal(@RequestBody JsonNode mealJson) throws JsonProcessingException {
        Meal meal;
        try {
            meal = objectMapper.treeToValue(mealJson, Meal.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }

        boolean isSaved = mealService.saveMeal(meal);
        if (isSaved) {
            return ResponseEntity.ok("{\"message\": \"Meal saved successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save meal\"}");
        }
    }

    @DeleteMapping("/deleteMeal/{id}")
    public ResponseEntity<String> deleteMeal(@RequestBody Long id) {
        boolean isDeleted = mealService.deleteMeal(id);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\": \"Meal deleted successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to delete meal\"}");
        }
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