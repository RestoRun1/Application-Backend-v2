package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Meal;
import com.restorun.backendapplication.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Save a meal", description = "Saves a new meal or updates an existing meal based on the provided details")
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
    public ResponseEntity<String> deleteMeal(@PathVariable Long id) {
        boolean deleted = mealService.deleteMeal(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();  // Responds with 404 if no restaurant was found
        }
        return ResponseEntity.ok("Meal deleted successfully");  // Confirm successful deletion
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