package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    // Autowire might be unnecessary.
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/retrieveRestaurantById")
    public ResponseEntity<Restaurant> retrieveRestaurantById(@RequestBody Long id) {
        Optional<Restaurant> restaurant = restaurantService.retrieveRestaurantById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveRestaurant")
    public ResponseEntity<String> saveRestaurant(@RequestBody String restaurant) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurantObj = mapper.readValue(restaurant, Restaurant.class);
        boolean saved = restaurantService.saveRestaurant(restaurantObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Restaurant saved successfully");
    }

    @DeleteMapping("/deleteRestaurant")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Long id) {
        Optional<Restaurant> restaurant = restaurantService.retrieveRestaurantById(id);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = restaurantService.deleteRestaurant(restaurant);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Restaurant deleted successfully");

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
