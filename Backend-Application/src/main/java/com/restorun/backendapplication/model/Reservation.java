package com.restorun.backendapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "table_id", nullable = false)
    private DiningTable table;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(length = 500)
    private String specialRequests;

    public Reservation(Long id, DiningTable table, Customer customer, LocalDateTime reservationTime, int numberOfGuests, String specialRequests){
        this.id = id;
        this.table = table;
        this.customer = customer;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.specialRequests = specialRequests;
    }

    public Long getTableId() {
        return table.getId();
    }

    public Long getCustomerId() {
        return customer.getUserId();
    }
    // Constructors, getters and setters are managed by Lombok

}
