package com.vamshi.complaint_management.controller;

import com.vamshi.complaint_management.dto.AuthResponse;
import com.vamshi.complaint_management.dto.LoginHistoryResponse;
import com.vamshi.complaint_management.dto.RegisterRequest;
import com.vamshi.complaint_management.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Get all users
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AuthResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // Create agent
    @PostMapping("/create-agent")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthResponse> createAgent(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(adminService.createAgent(
                request.getName(),
                request.getEmail(),
                request.getPassword()));
    }

    // Disable user
    @PutMapping("/users/{id}/disable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> disableUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.disableUser(id));
    }

    // Enable user
    @PutMapping("/users/{id}/enable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> enableUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.enableUser(id));
    }

    // Get all login history
    @GetMapping("/login-history")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LoginHistoryResponse>> getAllLoginHistory() {
        return ResponseEntity.ok(adminService.getAllLoginHistory());
    }

    // Get login history by user
    @GetMapping("/login-history/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<LoginHistoryResponse>> getUserLoginHistory(
            @PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getUserLoginHistory(userId));
    }
}