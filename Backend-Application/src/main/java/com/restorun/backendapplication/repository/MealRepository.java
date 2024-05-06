package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Meal;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT m FROM Meal m JOIN m.restaurant r WHERE r.id = :restaurant_id")
    ArrayList<Meal> findMealsByRestaurantId(@Param("restaurant_id") Long restaurant_id);

}
