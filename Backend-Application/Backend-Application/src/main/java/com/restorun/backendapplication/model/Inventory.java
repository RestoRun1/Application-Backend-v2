package com.restorun.backendapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Map;
import java.util.HashMap;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "inventory_items", joinColumns = @JoinColumn(name = "inventory_id"))
    @MapKeyColumn(name = "item_name")
    @Column(name = "quantity")
    private Map<String, Integer> items = new HashMap<>(); // Item names and their quantities
}
