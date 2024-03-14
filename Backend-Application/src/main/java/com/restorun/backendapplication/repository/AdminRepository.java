package com.restorun.backendapplication.repository;

import com.restorun.backendapplication.model.Admin;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

    Admin findByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}
