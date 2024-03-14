package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Admin;
import com.restorun.backendapplication.model.Bill;
import com.restorun.backendapplication.enums.PaymentStatus;
import com.restorun.backendapplication.repository.BillRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Bill> retrieveBillById(Long id) {
        return billRepository.findById(id);
    }

    public boolean saveBill(Bill bill) {
        billRepository.save(bill);
        return true;
    }

    public boolean deleteBill(Optional<Bill> bill) {
        if (bill.isPresent()){
            billRepository.delete(bill.get());
            return true;
        }
        return true;
    }

    public List<Bill> retrieveAllBills() {
        return billRepository.findAll();
    }

    public boolean updateBill(Bill bill) {
        return billRepository.findById(bill.getBillId())
                .map(existingBill -> {
                    existingBill.setTotalAmount(bill.getTotalAmount());
                    existingBill.setStatus(bill.getStatus());
                    existingBill.setTax(bill.getTax());
                    existingBill.setTip(bill.getTip());
                    billRepository.save(existingBill);

                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + bill.getBillId()));
    }

    public boolean makeBillUnpaid(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.UNPAID);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillPending(Optional<Bill> bill) {
        if(bill.isPresent()){
            bill.get().setStatus(PaymentStatus.PENDING);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillPaid(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.PAID);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillCancelled(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.CANCELLED);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillRefunded(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.REFUNDED);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillDeclined(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.DECLINED);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillFailed(Optional<Bill> bill) {
        if (bill.isPresent()){
            bill.get().setStatus(PaymentStatus.FAILED);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillPaidWithTip(Optional<Bill> bill, double tip) {
        if (bill.isPresent()){
            bill.get().setTip(tip);
            bill.get().setStatus(PaymentStatus.PAID);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }

    public boolean makeBillPaidWithTax(Optional<Bill> bill, double tax) {
        if (bill.isPresent()){
            bill.get().setTax(tax);
            bill.get().setStatus(PaymentStatus.PAID);
            billRepository.save(bill.get());
            return true;
        }
        return false;
    }


}
