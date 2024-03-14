package com.restorun.backendapplication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItem> menuItems = new HashSet<>();

    public Menu(Long id, String name, String description, Restaurant restaurant, Set<MenuItem> menuItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restaurant = restaurant;
        this.menuItems = menuItems;
    }

    public boolean addItem(MenuItem menuItem) {
        try{
            menuItems.add(menuItem);
            menuItem.setMenu(this);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
    public boolean removeItem(MenuItem menuItem) {
        try{
            menuItems.remove(menuItem);
            menuItem.setMenu(null);
            return true;
        }
        catch (Exception e){
            // throw new IllegalAccessException();
            return false;
        }
    }
}
