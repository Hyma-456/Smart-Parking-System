package com.smartparking.parkingservice.controller;

import com.smartparking.parkingservice.model.ParkingSlot;
import com.smartparking.parkingservice.service.ParkingSlotService;
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
@RequestMapping("/slots")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @Autowired
    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    // POST /slots - Create a new parking slot
    @PostMapping
    public ResponseEntity<?> createSlot(@RequestBody ParkingSlot slot) {
        try {
            ParkingSlot createdSlot = parkingSlotService.createSlot(slot);
            return new ResponseEntity<>(createdSlot, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET /slots - Get all parking slots
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        List<ParkingSlot> slots = parkingSlotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }

    // GET /slots/{id} - Get parking slot by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlotById(@PathVariable Long id) {
        return parkingSlotService.getSlotById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /slots/{id} - Update parking slot by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlot(@PathVariable Long id, @RequestBody ParkingSlot slotDetails) {
        try {
            ParkingSlot updatedSlot = parkingSlotService.updateSlot(id, slotDetails);
            return ResponseEntity.ok(updatedSlot);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /slots/{id} - Delete parking slot by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        try {
            parkingSlotService.deleteSlot(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
