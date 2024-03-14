package com.restorun.backendapplication.enums;

public enum PaymentStatus {
    UNPAID("UNPAID"),
    PENDING("PENDING"),
    PAID("PAID"),
    CANCELLED("CANCELLED"),
    REFUNDED("REFUNDED"),
    DECLINED("DECLINED"),
    FAILED("FAILED");

    private final String status;
    PaymentStatus(String status) {
        this.status = status;
    }
    public String getRole() {
        return status;
    }

}
