package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;
import com.coworking.coworkingspace.statusresponse.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customer/coworkingSpaces")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CoworkingSpaceRepo spaceRepo;

    @GetMapping
    public String viewAvailableSpaces(Model model) {
        populateSpacesAndReservations(model);
        return "reservations";
    }

    private void populateSpacesAndReservations(Model model) {
        List<CoworkingSpace> availableSpaces = spaceRepo.findCoworkingSpaceByAvailable(true);
        List<CoworkingSpace> reservations = spaceRepo.findByReservationDetailsIsNotNull();

        model.addAttribute("AvailableSpaces", availableSpaces);
        model.addAttribute("MyReservation", reservations);
    }

    @PostMapping("/reservations")
    public String reserveSpace(@RequestParam int id,
                               @RequestParam String reservationDetails,
                               Model model) {
        StatusResponse response;
        try {
            customerService.reserve(id, reservationDetails);
            response = StatusResponse.success("Reservation successful");
        } catch (Exception ex) {
            response = StatusResponse.error("Failed to reserve space: " + ex.getMessage());
        }
        model.addAttribute("reserveResponseDto", response);
        populateSpacesAndReservations(model);
        return "reservations";
    }


    @PostMapping("/reservations/cancel")
    public String cancelReservation(@RequestParam("id") Integer id) {
        customerService.cancelReservation(id);
        return "redirect:/customer/coworkingSpaces";
    }

    @GetMapping("/api/available")
    @ResponseBody
    public ResponseEntity<List<CoworkingSpace>> getAvailableSpacesApi() {
        List<CoworkingSpace> availableSpaces = spaceRepo.findCoworkingSpaceByAvailable(true);
        return ResponseEntity.ok(availableSpaces);
    }

    @GetMapping("/api/reservations")
    @ResponseBody
    public ResponseEntity<List<CoworkingSpace>> getReservationsApi() {
        List<CoworkingSpace> reservations = spaceRepo.findByReservationDetailsIsNotNull();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/api/reserve")
    @ResponseBody
    public ResponseEntity<StatusResponse> reserveSpaceApi(@RequestParam int id,
                                                          @RequestParam String bookingDetails) {
        try {
            customerService.reserve(id, bookingDetails);
            return ResponseEntity.ok(StatusResponse.success("Reservation successful"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(StatusResponse.error("Failed to reserve space: " + ex.getMessage()));
        }
    }

    @PostMapping("/api/cancel/{id}")
    @ResponseBody
    public ResponseEntity<StatusResponse> cancelReservationApi(@PathVariable Integer id) {
        try {
            customerService.cancelReservation(id);
            return ResponseEntity.ok(StatusResponse.success("Reservation canceled successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(StatusResponse.error("Failed to cancel reservation: " + e.getMessage()));
        }
    }
}
