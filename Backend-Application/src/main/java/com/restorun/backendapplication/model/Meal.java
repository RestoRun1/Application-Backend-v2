package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.MealCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private MealCategory category;

    @Column(nullable = false)
    private Double rating;

    // remove this later
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Meal(Long id, String name, String description, Double price, MealCategory category, Double rating, Menu menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.rating = rating;
        this.menu = menu;
    }
// Constructors, getters, setters, and other methods are handled by Lombok
}