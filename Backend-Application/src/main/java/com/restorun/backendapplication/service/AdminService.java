package com.restorun.backendapplication.service;

import com.restorun.backendapplication.dto.AuthenticatedUser;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class AdminService implements UserAuthenticationService{

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional(readOnly = true)
    public List<Admin> retrieveAllAdmins() {
        return adminRepository.findAll();
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String username, String password) {
        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        if (admin != null) {
            // Assuming the role is fetched or predefined, here just an example
            return new AuthenticatedUser(admin.getUsername(), Collections.singletonList("ROLE_ADMIN"));
        }
        return null;
    }

    @Transactional
    public boolean saveAdmin(Admin admin) {
        adminRepository.save(admin);
        return true;
    }

    @Transactional
    public boolean deleteAdmin(Long adminId) {
        if (adminRepository.existsById(adminId)) {
            adminRepository.deleteById(adminId);
            return true; // Admin found and deleted successfully
        } else {
            //throw new RuntimeException("Admin not found with id: " + adminId);
            return false;
        }
    }


    @Transactional
    public boolean updateAdmin(Admin admin) {
        return adminRepository.findById(admin.getUserId()) // Assuming the primary key is named `userId`
                .map(existingAdmin -> {
                    // Update the existing admin details with the new values from `admin`

                    // Set username if it's supposed to be updated
                    existingAdmin.setUsername(admin.getUsername());

                    // Set email if it's supposed to be updated
                    existingAdmin.setEmail(admin.getEmail());

                    // Set role if it's supposed to be updated
                    // Note: The `setRole` method expects a String, which will be converted to an enum
                    existingAdmin.setRole(admin.getRole());

                    // Save the updated admin back to the database
                    adminRepository.save(existingAdmin);

                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + admin.getUserId()));
    }


    public Admin retrieveAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }


}
