package com.smartparking.parkingservice.service;

import com.smartparking.parkingservice.model.ParkingSlot;
import com.smartparking.parkingservice.repository.ParkingRepository;
import com.smartparking.parkingservice.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository, ParkingRepository parkingRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingRepository = parkingRepository;
    }

    public ParkingSlot createSlot(ParkingSlot slot) {
        if (slot.getSlotNumber() == null || slot.getSlotNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("slotNumber is required (e.g., 'A1', 'B-101')");
        }
        if (slot.getParkingId() == null) {
            throw new IllegalArgumentException("parkingId is required to associate the slot with an existing Parking location");
        }
        if (!parkingRepository.existsById(slot.getParkingId())) {
            throw new IllegalArgumentException("Parking location with ID " + slot.getParkingId() + " does not exist");
        }
        if (slot.getStatus() == null || slot.getStatus().trim().isEmpty()) {
            slot.setStatus("AVAILABLE");
        }
        return parkingSlotRepository.save(slot);
    }

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public Optional<ParkingSlot> getSlotById(Long id) {
        return parkingSlotRepository.findById(id);
    }

    public ParkingSlot updateSlot(Long id, ParkingSlot slotDetails) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found with id: " + id));

        if (slotDetails.getSlotNumber() != null && !slotDetails.getSlotNumber().trim().isEmpty()) {
            slot.setSlotNumber(slotDetails.getSlotNumber());
        }
        if (slotDetails.getStatus() != null && !slotDetails.getStatus().trim().isEmpty()) {
            slot.setStatus(slotDetails.getStatus());
        }
        if (slotDetails.getParkingId() != null) {
            if (!parkingRepository.existsById(slotDetails.getParkingId())) {
                throw new IllegalArgumentException("Parking location with ID " + slotDetails.getParkingId() + " does not exist");
            }
            slot.setParkingId(slotDetails.getParkingId());
        }

        return parkingSlotRepository.save(slot);
    }

    public void deleteSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found with id: " + id));
        parkingSlotRepository.delete(slot);
    }
}
