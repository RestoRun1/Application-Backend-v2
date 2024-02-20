package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {
}
