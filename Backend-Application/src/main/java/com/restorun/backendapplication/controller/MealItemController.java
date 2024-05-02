/*
package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.MealItem;
import com.restorun.backendapplication.service.MealItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/mealItem")
public class MealItemController {
    private final MealItemService mealItemService;

    // Autowire might be unnecessary.
    @Autowired
    public MealItemController(MealItemService mealItemService) {
        this.mealItemService = mealItemService;
    }

    @GetMapping("/retrieveMealItemById")
    public ResponseEntity<MealItem> retrieveMealItemById(@RequestBody Long id) {
        Optional<MealItem> mealItem = mealItemService.retrieveMealItemById(id);
        return mealItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveMealItem")
    public ResponseEntity<String> saveMealItem(@RequestBody String mealItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MealItem mealItemObj = mapper.readValue(mealItem, MealItem.class);
        boolean saved = mealItemService.saveMealItem(mealItemObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("MealItem saved successfully");
    }

    @DeleteMapping("/deleteMealItem")
    public ResponseEntity<String> deleteMealItem(@RequestBody Long id) {
        Optional<MealItem> mealItem = mealItemService.retrieveMealItemById(id);
        if (mealItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = mealItemService.deleteMealItem(mealItem);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("MealItem deleted successfully");

    }


    // return all mealItems in the system
    @GetMapping("/retrieveAllMealItems")
    public ResponseEntity<List<MealItem>> retrieveAllMealItems() {
        List<MealItem> mealItems = mealItemService.retrieveAllMealItems();
        if (mealItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealItems);
    }
}
*/
