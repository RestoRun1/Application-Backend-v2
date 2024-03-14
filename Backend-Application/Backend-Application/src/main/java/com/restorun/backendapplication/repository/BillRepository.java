package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Bill;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface BillRepository extends JpaRepository<Bill, Long> {

    //List<Bill> retrieveBillsWithStatus(PaymentStatus status);


    // Think about bill creation date and time
}
