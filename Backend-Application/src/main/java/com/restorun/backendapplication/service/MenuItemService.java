/*
package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Menu;
import com.restorun.backendapplication.model.MenuItem;
import com.restorun.backendapplication.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional(readOnly = true)
    public Optional<MenuItem> retrieveMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public boolean saveMenuItem(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return true;
    }

    public boolean deleteMenuItem(Optional<MenuItem> menuItem) {
        if (menuItem.isPresent()){
            menuItemRepository.delete(menuItem.get());
            return true;
        }
        return true;
    }
    public boolean updateMenuItem(MenuItem menuItem) {
        return menuItemRepository.findById(menuItem.getId())
                .map(existingMenuItem -> {
                    existingMenuItem.setDescription(menuItem.getDescription());
                    existingMenuItem.setMenu(menuItem.getMenu());
                    existingMenuItem.setName(menuItem.getName());
                    existingMenuItem.setPrice(menuItem.getPrice());
                    menuItemRepository.save(existingMenuItem);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + menuItem.getId()));
    }

    public List<MenuItem> retrieveAllMenuItems(){ return menuItemRepository.findAll();}
}
*/
