package com.restorun.backendapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dining_table") // possible conflict with sql keyword "table"???? CHECK LATER
@Getter
@Setter
@NoArgsConstructor
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer tableNumber;

    @Column(nullable = false)
    private Integer seatingCapacity;

    // TODO
    public DiningTable(int tableNumber, int seatingCapacity){
        this.tableNumber = tableNumber;
        this.seatingCapacity = seatingCapacity;
    }
}
