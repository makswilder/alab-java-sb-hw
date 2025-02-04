package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.AdminService;
import com.coworking.coworkingspace.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {

    private final AdminService adminService;
    private final CustomerService customerService;

    public MainController(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    @PostMapping("/admin/add-space")
    public CoworkingSpace addSpace(@RequestBody CoworkingSpace space) {
        return adminService.addSpace(space);
    }

    @DeleteMapping("/admin/remove-space/{id}")
    public String removeSpace(@PathVariable int id) {
        adminService.removeSpace(id);
        return "Coworking Space Removed Successfully!";
    }

    @GetMapping("/admin/view-spaces")
    public List<CoworkingSpace> viewSpaces() {
        return adminService.viewSpaces();
    }

    @GetMapping("/customer/find-available-spaces")
    public List<CoworkingSpace> findAvailableSpaces() {
        return customerService.findAvailableSpaces();
    }
}
