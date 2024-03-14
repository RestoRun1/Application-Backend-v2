package com.restorun.backendapplication.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Entity
public class Logbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> ordersDelivered;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Bill> billsTaken;

    private String startShift;
    private String endShift;

    // default constr.
    public Logbook(){

    }

}
