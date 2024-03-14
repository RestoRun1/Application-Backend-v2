package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.MealItem;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface MealItemRepository extends JpaRepository<MealItem, Long> {
}
