package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Restaurant;
import com.restorun.backendapplication.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Restaurant> retrieveRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public boolean saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return true;
    }

    public boolean deleteRestaurant(Optional<Restaurant> restaurant) {
        if (restaurant.isPresent()){
            restaurantRepository.delete(restaurant.get());
            return true;
        }
        return true;
    }

    public List<Restaurant> retrieveAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public boolean updateRestaurant(Restaurant restaurant) {
        return restaurantRepository.findById(restaurant.getId())
                .map(existingRestaurant -> {
                    existingRestaurant.setAddress(existingRestaurant.getAddress());
                    existingRestaurant.setEmail(existingRestaurant.getEmail());
                    existingRestaurant.setEmployees(existingRestaurant.getEmployees());
                    existingRestaurant.setMenu(existingRestaurant.getMenu());
                    existingRestaurant.setEvents(existingRestaurant.getEvents());
                    existingRestaurant.setManagers(existingRestaurant.getManagers());
                    existingRestaurant.setName(existingRestaurant.getName());
                    existingRestaurant.setOccupancy(existingRestaurant.getOccupancy());
                    existingRestaurant.setPhoneNumber(existingRestaurant.getPhoneNumber());
                    restaurantRepository.save(existingRestaurant);

                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurant.getId()));
    }
}
