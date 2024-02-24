package com.restorun.backendapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_item")
@Getter
@Setter
@NoArgsConstructor
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 1024) // Assuming there might be a lengthy description
    private String description;

    // Assuming the existence of a Meal class that has a many-to-many relationship with MealItem
    // The relationship would typically be defined in the Meal class
}
