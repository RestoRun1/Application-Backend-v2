package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.enums.Role;
import com.restorun.backendapplication.model.Manager;
import com.restorun.backendapplication.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/saveManager")
    @Operation(summary = "Save a manager", description = "Saves a new manager or updates an existing manager based on the provided details")
    public ResponseEntity<String> saveManager(@RequestBody JsonNode managerJson){
        Long restaurantId = managerJson.get("restaurantId").asLong();
        String username = managerJson.get("username").asText();
        String email = managerJson.get("email").asText();
        String password = managerJson.get("password").asText();
        Role role = Role.valueOf(managerJson.get("role").asText());

        managerService.saveManager(restaurantId, username, email, password, role);
        return ResponseEntity.ok("{\"message\": \"Manager saved successfully\"}");

        /*Manager manager;
        try {
            manager = new ObjectMapper().treeToValue(managerJson, Manager.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error processing JSON\"}");
        }
        boolean isSaved = managerService.saveManager(manager);
        if (isSaved) {
            return ResponseEntity.ok("{\"message\": \"Manager saved successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to save manager\"}");
        }*/
    }
}
