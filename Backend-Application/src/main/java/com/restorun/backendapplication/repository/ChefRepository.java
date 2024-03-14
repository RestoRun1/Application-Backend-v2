package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Chef;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ChefRepository extends JpaRepository<Chef, Long> {
}