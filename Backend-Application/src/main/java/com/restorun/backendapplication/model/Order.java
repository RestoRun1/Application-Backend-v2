package com.restorun.backendapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "order_meals")
    @JsonIgnore
    private List<Meal> meals; // A list of meals in the order

    @Column(nullable = false)
    private Double totalPrice;

    // @TODO MEALS NEEDS TO HAVE QUANTITY PROPERTY
    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // "ready", "pending", "etc..."

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", nullable = false)
    @JsonIgnore
    private DiningTable table;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    /*@Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;*/

    public Order(Long id, List<Meal> meals, Double totalPrice, Integer quantity, PaymentStatus status, DiningTable table, Customer customer) {
        this.id = id;
        this.meals = meals;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.status = status;
        this.table = table;
        this.customer = customer;
    }

    // Expose tableId
    @JsonProperty("tableId")
    public Long getTableId() {
        return this.table != null ? this.table.getId() : null;
    }

    // Expose customerId
    @JsonProperty("customerId")
    public Long getCustomerId() {
        return this.customer != null ? this.customer.getUserId() : null;
    }
// Constructors, getters, setters, and other methods are handled by Lombok
}
