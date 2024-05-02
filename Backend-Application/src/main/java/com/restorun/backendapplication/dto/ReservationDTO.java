package com.restorun.backendapplication.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private DiningTableDTO table;
    private CustomerDTO customer;
    private LocalDateTime reservationTime;
    private int numberOfGuests;
    private String specialRequests;

    // Getters and setters
    public DiningTableDTO getTable() {
        return table;
    }

    public void setTable(DiningTableDTO table) {
        this.table = table;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
}
