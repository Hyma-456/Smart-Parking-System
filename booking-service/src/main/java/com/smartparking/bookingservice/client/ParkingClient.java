package com.smartparking.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "PARKING-SERVICE")
public interface ParkingClient {

    @GetMapping("/parkings/{id}/available")
    Map<String, Object> getAvailableSlots(@PathVariable("id") Long id);

    @GetMapping("/slots/{id}")
    Map<String, Object> getSlotById(@PathVariable("id") Long id);
}
