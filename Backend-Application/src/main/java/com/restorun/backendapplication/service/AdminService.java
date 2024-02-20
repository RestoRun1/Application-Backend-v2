package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> retrieveAllAdmins() {
        return adminRepository.findAll();
    }

    public boolean saveAdmin(Admin admin) {
        adminRepository.save(admin);
        return true;
    }

    public boolean deleteAdmin(Long admin) {
        adminRepository.deleteById(admin);
        return true;
    }

    public boolean updateAdmin(Admin admin) {
        adminRepository.save(admin);
        return true;
    }

    public Admin retrieveAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }


}
