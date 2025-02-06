package com.coworking.coworkingspace.controller;


import com.coworking.coworkingspace.model.Reservations;
import com.coworking.coworkingspace.service.CustomerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/reserve")
    public String makeReservation(
            @RequestParam int spaceID,
            @RequestParam String customerName,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            Model model) {

        try {
            Reservations reservations = customerService.makeReservation(spaceID, customerName, date, startTime, endTime);
            model.addAttribute("successMessage", "Reservation successful!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/reservations";
    }

    @GetMapping
    public String viewReservations(@RequestParam(required = false) String customerName, Model model) {
        List<Reservations> reservations = customerService.viewMyReservations(customerName);
        model.addAttribute("MyReservation", reservations);
        return "reservations";
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam int id, Model model) {

        try {
            customerService.cancelReservation(id);
            model.addAttribute("successMessage", "Reservation canceled successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/reservations";
    }
}
