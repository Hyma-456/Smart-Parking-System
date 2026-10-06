package com.smartparking.parkingservice.repository;

import com.smartparking.parkingservice.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
}