package com.restorun.backendapplication.model;

import com.restorun.backendapplication.model.Restaurant;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class RestaurantTest {

    @Test
    public void testRestaurantCreation() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        // Add other setters here

        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test St", restaurant.getAddress());
        // Add other assertions here
    }
}
