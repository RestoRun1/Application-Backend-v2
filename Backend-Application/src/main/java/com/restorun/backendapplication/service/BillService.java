package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.enums.PaymentStatus;
import com.restorun.backendapplication.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }



    public Optional<Bill> retrieveBillById(Long id) {
        return billRepository.findById(id);
    }

    public boolean saveBill(Bill bill) {
        billRepository.save(bill);
        return true;
    }

    public boolean deleteBill(Optional<Bill> bill) {
        billRepository.delete(bill.get());
        return true;
    }

    public List<Bill> retrieveAllBills() {
        return billRepository.findAll();
    }

    public boolean makeBillUnpaid(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.UNPAID);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillPending(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.PENDING);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillPaid(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.PAID);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillCancelled(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.CANCELLED);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillRefunded(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.REFUNDED);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillDeclined(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.DECLINED);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillFailed(Optional<Bill> bill) {
        bill.get().setStatus(PaymentStatus.FAILED);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillPaidWithTip(Optional<Bill> bill, double tip) {
        bill.get().setTip(tip);
        bill.get().setStatus(PaymentStatus.PAID);
        billRepository.save(bill.get());
        return true;
    }

    public boolean makeBillPaidWithTax(Optional<Bill> bill, double tax) {
        bill.get().setTax(tax);
        bill.get().setStatus(PaymentStatus.PAID);
        billRepository.save(bill.get());
        return true;
    }


}
