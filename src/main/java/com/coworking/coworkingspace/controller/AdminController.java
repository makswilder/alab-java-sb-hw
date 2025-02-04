package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/spaces")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<CoworkingSpace> getAllSpaces() {
        return adminService.viewSpaces();
    }

    @PostMapping
    public ResponseEntity<String> addSpace(@RequestBody CoworkingSpace space) {
        adminService.addSpace(space);
        return ResponseEntity.ok("Space added successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable int id) {
        adminService.removeSpace(id);
        return ResponseEntity.ok("Space deleted successfully!");
    }
}
