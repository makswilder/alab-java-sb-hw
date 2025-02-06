package com.coworking.coworkingspace.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.coworking.coworkingspace.model.CoworkingSpace;
import com.coworking.coworkingspace.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // For Loading spaces.html
    @GetMapping("/spaces")
    public String viewSpaces(Model model) {
        List<CoworkingSpace> spaces = adminService.viewSpaces();
        model.addAttribute("coworkingSpaces", spaces);
        return "spaces";
    }

    // For Loading spaces.html
    @GetMapping("/add")
    public String addSpace(Model model) {
        model.addAttribute("space", new CoworkingSpace());
        return "addSpace";
    }

   @PostMapping("/add")
   public String addSpace(@ModelAttribute CoworkingSpace space) {
        adminService.addSpace(space);
        return "redirect:/admin/spaces";
   }

    @GetMapping("/remove/{id}")
    public String removeSpace(@PathVariable int id) {
        adminService.removeSpace(id);
        return "redirect:/admin/spaces";
    }
}
