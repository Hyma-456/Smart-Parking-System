package com.smartparking.parkingservice.repository;

import com.smartparking.parkingservice.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
