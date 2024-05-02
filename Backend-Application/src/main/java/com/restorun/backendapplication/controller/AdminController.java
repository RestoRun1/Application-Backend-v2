package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieveAllAdmins")
    public ResponseEntity<List<Admin>> retrieveAllAdmins() {
        List<Admin> admins = adminService.retrieveAllAdmins();
        if (admins.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admins);
    }

    // method infos needs to be fixed. not working as a controller.
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieveAdminById")
    public ResponseEntity<Admin> retrieveAdminById(@RequestBody String admin) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Admin adminObj = mapper.readValue(admin, Admin.class);

        Admin dbResponse = adminService.retrieveAdminById(adminObj.getUserId());

        if (dbResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dbResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<String> deleteAdmin(@RequestBody String id) {
        boolean deleted = adminService.deleteAdmin(Long.valueOf(id));
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Admin deleted successfully");
    }

    /**
     *
     * @param admin
     * @return
     * @throws JsonProcessingException
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody String admin ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Admin adminObj = mapper.readValue(admin, Admin.class);

        boolean saved = adminService.saveAdmin(adminObj);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Admin saved successfully");

    }
}
