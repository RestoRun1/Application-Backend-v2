package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    //List<Bill> retrieveBillsWithStatus(PaymentStatus status);


    // Think about bill creation date and time
}
