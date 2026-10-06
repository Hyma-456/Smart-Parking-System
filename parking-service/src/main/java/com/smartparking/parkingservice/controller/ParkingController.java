package com.smartparking.parkingservice.controller;

import com.smartparking.parkingservice.model.Parking;
import com.smartparking.parkingservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    // POST /parkings - Create new parking location
    @PostMapping
    public ResponseEntity<Parking> createParking(@RequestBody Parking parking) {
        Parking createdParking = parkingService.createParking(parking);
        return new ResponseEntity<>(createdParking, HttpStatus.CREATED);
    }

    // GET /parkings - Get all parking locations
    @GetMapping
    public ResponseEntity<List<Parking>> getAllParkings() {
        List<Parking> parkings = parkingService.getAllParkings();
        return ResponseEntity.ok(parkings);
    }

    // GET /parkings/{id} - Get parking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingById(@PathVariable Long id) {
        return parkingService.getParkingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /parkings/{id} - Update parking by ID
    @PutMapping("/{id}")
    public ResponseEntity<Parking> updateParking(@PathVariable Long id, @RequestBody Parking parkingDetails) {
        try {
            Parking updatedParking = parkingService.updateParking(id, parkingDetails);
            return ResponseEntity.ok(updatedParking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /parkings/{id} - Delete parking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        try {
            parkingService.deleteParking(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /parkings/{id}/available - Get available slot count for a parking location
    @GetMapping("/{id}/available")
    public ResponseEntity<Map<String, Object>> getAvailableSlots(@PathVariable Long id) {
        try {
            Integer availableSlots = parkingService.getAvailableSlots(id);
            Map<String, Object> response = new HashMap<>();
            response.put("parkingId", id);
            response.put("availableSlots", availableSlots);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
