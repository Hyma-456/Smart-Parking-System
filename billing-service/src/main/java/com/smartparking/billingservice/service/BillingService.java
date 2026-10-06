package com.smartparking.billingservice.service;

import com.smartparking.billingservice.model.Billing;
import com.smartparking.billingservice.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    @Autowired
    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public Billing generateBill(Billing billing) {
        if (billing.getBillingTime() == null) {
            billing.setBillingTime(LocalDateTime.now());
        }
        if (billing.getPaymentStatus() == null) {
            billing.setPaymentStatus("PAID");
        }
        return billingRepository.save(billing);
    }

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillById(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing record not found with id: " + id));
    }

    public List<Billing> getBillsByUserId(Long userId) {
        return billingRepository.findByUserId(userId);
    }
}
