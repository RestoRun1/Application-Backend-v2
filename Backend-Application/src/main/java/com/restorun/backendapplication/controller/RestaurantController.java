package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ObjectMapper objectMapper;

    private final ObjectMapper objectMapper;

    // Autowire might be unnecessary.
    @Autowired
    public RestaurantController(RestaurantService restaurantService, ObjectMapper objectMapper) {
        this.restaurantService = restaurantService;
        this.objectMapper = objectMapper;
    }

    //@TODO ADD {ID} TO THE END OF API
    @GetMapping("/retrieveRestaurantById")
    public ResponseEntity<Restaurant> retrieveRestaurantById(@RequestBody Long id) {
        Optional<Restaurant> restaurant = restaurantService.retrieveRestaurantById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/saveRestaurant")
    @Operation(summary = "Save a restaurant", description = "Saves a new restaurant or updates an existing restaurant based on the provided details")
    public ResponseEntity<String> saveRestaurant(@RequestBody JsonNode restaurantJson) {
        // Convert JsonNode to Restaurant object
        Restaurant restaurant;
        try {
            restaurant = objectMapper.treeToValue(restaurantJson, Restaurant.class);
        } catch (JsonProcessingException e) {
            // Return an error response if there's an issue with JSON processing
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }

        // Attempt to save the restaurant using your service layer
        boolean isSaved = restaurantService.saveRestaurant(restaurant);
        if (isSaved) {
            // Return a success response
            return ResponseEntity.ok("{\"message\": \"Restaurant saved successfully\"}");
        } else {
            // Return an error response if saving the restaurant failed
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save restaurant\"}");
        }
    }

    @DeleteMapping("/deleteRestaurant/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        boolean deleted = restaurantService.deleteRestaurant(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();  // Responds with 404 if no restaurant was found
        }
        return ResponseEntity.ok("Restaurant deleted successfully");  // Confirm successful deletion
    }

    // return all restaurants in the system
    @GetMapping("/retrieveAllRestaurants")
    public ResponseEntity<List<Restaurant>> retrieveAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.retrieveAllRestaurants();
        if (restaurants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurants);
    }
}
