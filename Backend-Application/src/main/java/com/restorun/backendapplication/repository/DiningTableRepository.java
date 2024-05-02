package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.DiningTable;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
}
