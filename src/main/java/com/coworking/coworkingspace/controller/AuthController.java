package com.coworking.coworkingspace.controller;

import com.coworking.coworkingspace.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.coworking.coworkingspace.configs.JwtUtil;
import com.coworking.coworkingspace.model.Role;
import com.coworking.coworkingspace.repository.UserRepo;

import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(defaultValue = "CUSTOMER") Role role,
                               Model model) {
        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("errorMsg", "User already exists");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setURole(role);

        userRepo.save(newUser);
        model.addAttribute("msg", "User registered successfully. Please login.");
        return "login";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginApi(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> registerApi(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam(defaultValue = "CUSTOMER") Role role) {
        if (userRepo.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setURole(role);

        userRepo.save(newUser);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
