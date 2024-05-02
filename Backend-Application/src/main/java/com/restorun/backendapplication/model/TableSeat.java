/*
package com.restorun.backendapplication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "table_seat")
public class TableSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dining_table_id")
    private DiningTable diningTable;

    public TableSeat() {
    }

    public TableSeat(int seatNumber, DiningTable diningTable) {
        this.seatNumber = seatNumber;
        this.diningTable = diningTable;
    }

    public Long getId() {
        return id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

}
*/
