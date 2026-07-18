package com.vamshi.complaint_management.controller;

import com.vamshi.complaint_management.dto.ComplaintResponse;
import com.vamshi.complaint_management.dto.DashboardResponse;
import com.vamshi.complaint_management.enums.Category;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.enums.Priority;
import com.vamshi.complaint_management.service.ComplaintService;
import com.vamshi.complaint_management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final ComplaintService complaintService;

    // ADMIN: get dashboard stats
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }

    // ADMIN: search and filter complaints
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> searchComplaints(
            @RequestParam(required = false) ComplaintStatus status,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Priority priority) {
        return ResponseEntity.ok(
                complaintService.searchComplaints(status, category, priority));
    }
}