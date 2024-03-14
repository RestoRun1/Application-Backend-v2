package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Menu;
import com.restorun.backendapplication.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    // Autowire might be unnecessary.
    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/retrieveMenuById")
    public ResponseEntity<Menu> retrieveMenuById(@RequestBody Long id) {
        Optional<Menu> menu = menuService.retrieveMenuById(id);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveMenu")
    public ResponseEntity<String> saveMenu(@RequestBody String menu) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Menu menuObj = mapper.readValue(menu, Menu.class);
        boolean saved = menuService.saveMenu(menuObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Menu saved successfully");
    }

    @DeleteMapping("/deleteMenu")
    public ResponseEntity<String> deleteMenu(@RequestBody Long id) {
        Optional<Menu> menu = menuService.retrieveMenuById(id);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = menuService.deleteMenu(menu);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Menu deleted successfully");

    }


    // return all menus in the system
    @GetMapping("/retrieveAllMenus")
    public ResponseEntity<List<Menu>> retrieveAllMenus() {
        List<Menu> menus = menuService.retrieveAllMenus();
        if (menus.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menus);
    }
}
