package com.vamshi.complaint_management.controller;

import com.vamshi.complaint_management.dto.ComplaintRequest;
import com.vamshi.complaint_management.dto.ComplaintResponse;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    // USER: raise complaint
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ComplaintResponse> raiseComplaint(
            @Valid @RequestBody ComplaintRequest request) {
        return ResponseEntity.ok(complaintService.raiseComplaint(request));
    }

    // USER: view my complaints
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints() {
        return ResponseEntity.ok(complaintService.getMyComplaints());
    }

    // AGENT: view assigned complaints
    @GetMapping("/assigned")
    @PreAuthorize("hasAnyAuthority('AGENT', 'ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> getAssignedComplaints() {
        return ResponseEntity.ok(complaintService.getAssignedComplaints());
    }

    // AGENT: update complaint status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('AGENT', 'ADMIN')")
    public ResponseEntity<ComplaintResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ComplaintStatus status,
            @RequestParam(required = false) String resolutionNotes) {
        return ResponseEntity.ok(
                complaintService.updateStatus(id, status, resolutionNotes));
    }

    // ADMIN: view all complaints
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // ADMIN: assign complaint to agent
    @PutMapping("/{id}/assign/{agentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ComplaintResponse> assignComplaint(
            @PathVariable Long id,
            @PathVariable Long agentId) {
        return ResponseEntity.ok(complaintService.assignComplaint(id, agentId));
    }

    // ADMIN: get all agents
    @GetMapping("/agents")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllAgents() {
        return ResponseEntity.ok(complaintService.getAllAgents());
    }
}
