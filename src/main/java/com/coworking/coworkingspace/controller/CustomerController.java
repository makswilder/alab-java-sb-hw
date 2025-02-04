package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.Reservations;
import com.coworking.coworkingspace.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/available-spaces")
    public List<CoworkingSpace> getAvailableSpaces() {
        return customerService.findAvailableSpaces();
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> makeReservation(
            @RequestParam int spaceID,
            @RequestParam String customerName,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime) {

        customerService.makeReservation(spaceID, customerName, date, startTime, endTime);
        return ResponseEntity.ok("Reservation successful!");
    }

    @GetMapping("/reservations")
    public List<Reservations> viewReservations(@RequestParam String customerName) {
        return customerService.viewMyReservations(customerName);
    }

    @DeleteMapping("/cancel/{reservationID}")
    public ResponseEntity<String> cancelReservation(@PathVariable int reservationID) {
        customerService.cancelReservation(reservationID);
        return ResponseEntity.ok("Reservation canceled successfully!");
    }
}
