package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Manager;
import com.restorun.backendapplication.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private final ManagerService managerService;
    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    @GetMapping("/retrieveAllManagers")
    public ResponseEntity<List<Manager>> retrieveAllManagers() {
        List<Manager> managers = managerService.retrieveAllManagers();
        if (managers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(managers);
    }

    // method infos needs to be fixed. not working as a controller.
    @GetMapping("/retrieveManagerById")
    public ResponseEntity<Manager> retrieveManagerById(@RequestBody String manager) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Manager managerObj = mapper.readValue(manager, Manager.class);

        Manager dbResponse = managerService.retrieveManagerById(managerObj.getUserId());

        if (dbResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dbResponse);
    }

    @DeleteMapping("/deleteManager")
    public ResponseEntity<String> deleteManager(@RequestBody String id) {
        boolean deleted = managerService.deleteManager(Long.valueOf(id));
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Manager deleted successfully");
    }

    @PostMapping("/addManager")
    public ResponseEntity<String> addManager(@RequestBody String manager ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Manager managerObj = mapper.readValue(manager, Manager.class);

        boolean saved = managerService.saveManager(managerObj);
        if (!saved) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Manager saved successfully");

    }
}
