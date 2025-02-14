package com.coworking.coworkingspace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home() {
        return "main.html";
    }

    @GetMapping("/api/main")
    @ResponseBody
    public ResponseEntity<String> getHomeApi() {
        return ResponseEntity.ok("Welcome to the Coworking Space Reservation System!");
    }
}
