package com.restorun.backendapplication.model;

import com.restorun.backendapplication.enums.PaymentStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {

    // properties of the class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private double totalAmount;

    private double tip;

    private double tax;

    private PaymentStatus status;

    // constructors
    public Bill() {
    }

    public Bill(Long billId, double totalAmount, double tip, double tax, PaymentStatus status) {
        this.billId = billId;
        this.totalAmount = totalAmount;
        this.tip = tip;
        this.tax = tax;
        this.status = status;
    }

    public Bill(double totalAmount, double tip, double tax, PaymentStatus status) {
        this.totalAmount = totalAmount;
        this.tip = tip;
        this.tax = tax;
        this.status = status;
    }

    // getters and setters

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", totalAmount=" + totalAmount +
                ", tip=" + tip +
                ", tax=" + tax +
                ", status=" + status +
                '}';
    }

    /*
    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;

        Bill bill = (Bill) o;

        if (Double.compare(bill.totalAmount, totalAmount) != 0) return false;
        if (Double.compare(bill.tip, tip) != 0) return false;
        if (Double.compare(bill.tax, tax) != 0) return false;
        if (billId != null ? !billId.equals(bill.billId) : bill.billId != null) return false;
        return status == bill.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billId != null ? billId.hashCode() : 0;
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(tip);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(tax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
    */

    // to JSON method
    public String toJSON() {
        return "{" +
                "\"billId\":\"" + billId + "\"" +
                ", \"totalAmount\":" + totalAmount +
                ", \"tip\":" + tip +
                ", \"tax\":" + tax +
                ", \"status\":\"" + status + "\"" +
                "}";
    }

}
