package com.smartparking.billingservice.controller;

import com.smartparking.billingservice.model.Billing;
import com.smartparking.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billings")
public class BillingController {

    private final BillingService billingService;

    @Autowired
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<Billing> createBill(@RequestBody Billing billing) {
        Billing createdBill = billingService.generateBill(billing);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {
        return ResponseEntity.ok(billingService.getAllBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(billingService.getBillById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Billing>> getBillsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(billingService.getBillsByUserId(userId));
    }
}
