package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Menu;
import com.restorun.backendapplication.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Menu> retrieveMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public boolean saveMenu(Menu menu) {
        menuRepository.save(menu);
        return true;
    }

    public boolean deleteMenu(Optional<Menu> menu) {
        if (menu.isPresent()){
            menuRepository.delete(menu.get());
            return true;
        }
        return true;
    }
    public boolean updateMenu(Menu menu) {
        return menuRepository.findById(menu.getId())
                .map(existingMenu -> {
                    existingMenu.setDescription(existingMenu.getDescription());
                    existingMenu.setMenuItems(existingMenu.getMenuItems());
                    existingMenu.setName(existingMenu.getName());
                    existingMenu.setRestaurant(existingMenu.getRestaurant());
                    menuRepository.save(existingMenu);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menu.getId()));
    }

    public List<Menu> retrieveAllMenus(){return menuRepository.findAll();}
}
