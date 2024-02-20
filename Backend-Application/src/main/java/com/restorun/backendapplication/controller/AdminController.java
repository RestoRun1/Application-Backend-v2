package com.restorun.backendapplication.controller;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "Admin Controller")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/retrieveAllAdmins")
    public ResponseEntity<List<Admin>> retrieveAllAdmins() {
        List<Admin> admins = adminService.retrieveAllAdmins();
        if (admins.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/retrieveAdminById")
    public ResponseEntity<Admin> retrieveAdminById(@RequestBody Long id) {
        Admin admin = adminService.retrieveAdminById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/deleteAdmin")
    public ResponseEntity<String> deleteAdmin(@RequestBody Long id) {
        boolean deleted = adminService.deleteAdmin(id);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @GetMapping("/saveAdmin")
    public ResponseEntity<String> saveAdmin(Admin admin) {
        boolean saved = adminService.saveAdmin(admin);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Admin saved successfully");
    }
}
