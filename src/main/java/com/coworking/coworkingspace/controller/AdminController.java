package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.repository.CoworkingSpaceRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.coworking.coworkingspace.statusresponse.StatusResponse;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.model.TypeOfSpace;
import com.coworking.coworkingspace.service.AdminService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/coworkingSpaces")
public class AdminController {

    private final CoworkingSpaceRepo coworkingSpaceRepo;
    private final AdminService adminService;

    public AdminController(CoworkingSpaceRepo coworkingSpaceRepo, AdminService adminService) {
        this.coworkingSpaceRepo = coworkingSpaceRepo;
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String viewCoworkingSpaces(Model model, Principal principal) {
        populateSpaces(model);
        return "coworkingSpaces.html";
    }

    private void populateSpaces(Model model) {
        List<CoworkingSpace> spaces = coworkingSpaceRepo.findAll();
        model.addAttribute("coworkingSpaces", spaces);
        model.addAttribute("types", TypeOfSpace.values());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String addCoworkingSpace(@RequestParam String name,
                                    @RequestParam String type,
                                    @RequestParam double price) {
        CoworkingSpace space = adminService.createSpace(name, type, price);
        adminService.addSpace(space);
        return "redirect:/admin/coworkingSpaces";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete")
    public String deleteCoworkingSpace(@RequestParam("id") Integer id, Model model) {
        try {
            adminService.removeSpace(id);
            model.addAttribute("deleteResponse", StatusResponse.success("Coworking space deleted successfully."));
        } catch (Exception ex) {
            model.addAttribute("deleteResponse", StatusResponse.error("Failed to delete space: " + ex.getMessage()));
        }
        populateSpaces(model);
        return "coworkingSpaces.html";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateCoworkingSpace(@RequestParam("id") int id,
                                       @RequestParam("name") String name,
                                       @RequestParam("type") String type,
                                       @RequestParam("price") double price,
                                       Model model) {
        CoworkingSpace updatedSpace = adminService.createSpace(name, type, price);
        try {
            adminService.updateSpace(id, updatedSpace);
            model.addAttribute("updateResponse", StatusResponse.success("Coworking space updated successfully."));
        } catch (Exception ex) {
            model.addAttribute("updateResponse", StatusResponse.error("Failed to update space: " + ex.getMessage()));
        }
        populateSpaces(model);
        return "coworkingSpaces.html";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<CoworkingSpace>> getAllSpacesApi() {
        List<CoworkingSpace> spaces = coworkingSpaceRepo.findAll();
        return ResponseEntity.ok(spaces);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/add")
    @ResponseBody
    public ResponseEntity<?> addCoworkingSpaceApi(@RequestParam String name,
                                                  @RequestParam String type,
                                                  @RequestParam double price) {
        try {
            CoworkingSpace space = adminService.createSpace(name, type, price);
            adminService.addSpace(space);
            return ResponseEntity.status(HttpStatus.CREATED).body(space);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCoworkingSpaceApi(@PathVariable int id) {
        try {
            adminService.removeSpace(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCoworkingSpaceApi(@PathVariable int id,
                                                     @RequestBody CoworkingSpace updatedSpace) {
        try {
            adminService.updateSpace(id, updatedSpace);
            CoworkingSpace result = coworkingSpaceRepo.findById(id).orElseThrow();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
