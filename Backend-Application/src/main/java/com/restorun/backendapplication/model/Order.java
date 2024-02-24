package com.restorun.backendapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_meals",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals; // A list of meals in the order

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private String status; // "ready", "pending", "etc..."

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private Table diningTable; // Assuming a DiningTable class exists

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id")
    private Kitchen kitchen; // Link to the Kitchen handling the order

    // Constructors, getters, setters, and other methods are handled by Lombok
}
