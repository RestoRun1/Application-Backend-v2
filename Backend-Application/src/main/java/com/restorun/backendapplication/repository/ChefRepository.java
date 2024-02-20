package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRepository extends JpaRepository<Chef, Long> {
}