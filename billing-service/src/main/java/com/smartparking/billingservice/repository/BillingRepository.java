package com.smartparking.billingservice.repository;

import com.smartparking.billingservice.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByUserId(Long userId);
    List<Billing> findByBookingId(Long bookingId);
}
