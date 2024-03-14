package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Payment;
import com.restorun.backendapplication.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Payment> retrievePaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public boolean savePayment(Payment payment) {
        paymentRepository.save(payment);
        return true;
    }

    public boolean deletePayment(Optional<Payment> payment) {
        if (payment.isPresent()){
            paymentRepository.delete(payment.get());
            return true;
        }
        return true;
    }
    public boolean updatePayment(Payment payment) {
        return paymentRepository.findById(payment.getId())
                .map(existingPayment -> {
                    existingPayment.setOrder(payment.getOrder());
                    existingPayment.setDiningTable(payment.getDiningTable());
                    paymentRepository.save(existingPayment);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + payment.getId()));
    }

    public List<Payment> retrieveAllPayments(){return paymentRepository.findAll();}
}
