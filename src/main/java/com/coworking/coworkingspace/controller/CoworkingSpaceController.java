package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.Exceptions.SpaceNotFoundException;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.CoworkingSpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class CoworkingSpaceController {

    private final CoworkingSpaceService service;

    public CoworkingSpaceController(CoworkingSpaceService service) {
        this.service = service;
    }

    @GetMapping
    public List<CoworkingSpace> getAllSpaces() {
        return service.getSpaces();
    }

    @PostMapping
    public ResponseEntity<String> addSpace(@RequestBody CoworkingSpace space) {
        service.saveSpace(space);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Space added successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable int id) {
        try {
            service.deleteSpace(id);
            return ResponseEntity
                    .ok("Space deleted successfully!");
        } catch (RuntimeException | SpaceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
