package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer_orders")
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
    private PaymentStatus status; // "ready", "pending", "etc..."

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private DiningTable diningTable; // Assuming a DiningTable class exists

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Order(Long id, List<Meal> meals, Double totalPrice, PaymentStatus status, DiningTable diningTable) {
        this.id = id;
        this.meals = meals;
        this.totalPrice = totalPrice;
        this.status = status;
        this.diningTable = diningTable;
    }
// Constructors, getters, setters, and other methods are handled by Lombok
}
