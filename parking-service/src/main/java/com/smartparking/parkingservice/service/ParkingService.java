package com.smartparking.parkingservice.service;

import com.smartparking.parkingservice.model.Parking;
import com.smartparking.parkingservice.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public Parking createParking(Parking parking) {
        if (parking.getAvailableSlots() == null) {
            parking.setAvailableSlots(parking.getTotalSlots());
        }
        return parkingRepository.save(parking);
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Optional<Parking> getParkingById(Long id) {
        return parkingRepository.findById(id);
    }

    public Parking updateParking(Long id, Parking parkingDetails) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking location not found with id: " + id));

        parking.setLocation(parkingDetails.getLocation());
        parking.setTotalSlots(parkingDetails.getTotalSlots());
        parking.setAvailableSlots(parkingDetails.getAvailableSlots());

        return parkingRepository.save(parking);
    }

    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking location not found with id: " + id));
        parkingRepository.delete(parking);
    }

    public Integer getAvailableSlots(Long id) {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking location not found with id: " + id));
        return parking.getAvailableSlots();
    }
}
