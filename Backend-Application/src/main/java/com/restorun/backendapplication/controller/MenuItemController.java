/*
package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.MenuItem;
import com.restorun.backendapplication.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menuItem")
public class MenuItemController {
    private final MenuItemService menuItemService;

    // Autowire might be unnecessary.
    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/retrieveMenuItemById")
    public ResponseEntity<MenuItem> retrieveMenuItemById(@RequestBody Long id) {
        Optional<MenuItem> menuItem = menuItemService.retrieveMenuItemById(id);
        return menuItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveMenuItem")
    public ResponseEntity<String> saveMenuItem(@RequestBody String menuItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MenuItem menuItemObj = mapper.readValue(menuItem, MenuItem.class);
        boolean saved = menuItemService.saveMenuItem(menuItemObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("MenuItem saved successfully");
    }

    @DeleteMapping("/deleteMenuItem")
    public ResponseEntity<String> deleteMenuItem(@RequestBody Long id) {
        Optional<MenuItem> menuItem = menuItemService.retrieveMenuItemById(id);
        if (menuItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = menuItemService.deleteMenuItem(menuItem);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("MenuItem deleted successfully");

    }


    @GetMapping("/retrieveAllMenuItems")
    public ResponseEntity<List<MenuItem>> retrieveAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.retrieveAllMenuItems();
        if (menuItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menuItems);
    }
}
*/
