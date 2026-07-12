package com.vamshi.complaint_management.service;

import com.vamshi.complaint_management.config.UserDetailsImpl;
import com.vamshi.complaint_management.dto.ComplaintRequest;
import com.vamshi.complaint_management.dto.ComplaintResponse;
import com.vamshi.complaint_management.entity.Complaint;
import com.vamshi.complaint_management.entity.User;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.repository.ComplaintRepository;
import com.vamshi.complaint_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    private ComplaintResponse mapToResponse(Complaint complaint) {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .priority(complaint.getPriority())
                .status(complaint.getStatus())
                .userName(complaint.getUser().getName())
                .userEmail(complaint.getUser().getEmail())
                .agentName(complaint.getAgent() != null ? complaint.getAgent().getName() : null)
                .agentEmail(complaint.getAgent() != null ? complaint.getAgent().getEmail() : null)
                .resolutionNotes(complaint.getResolutionNotes())
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .build();
    }

    // USER: raise a complaint
    public ComplaintResponse raiseComplaint(ComplaintRequest request) {
        User currentUser = getCurrentUser();
        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .priority(request.getPriority())
                .user(currentUser)
                .build();
        return mapToResponse(complaintRepository.save(complaint));
    }

    // USER: view my complaints
    public List<ComplaintResponse> getMyComplaints() {
        User currentUser = getCurrentUser();
        return complaintRepository.findByUser(currentUser)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // AGENT: view assigned complaints
    public List<ComplaintResponse> getAssignedComplaints() {
        User currentUser = getCurrentUser();
        return complaintRepository.findByAgent(currentUser)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // AGENT: update complaint status
    public ComplaintResponse updateStatus(Long complaintId,
                                          ComplaintStatus status,
                                          String resolutionNotes) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        if (resolutionNotes != null) {
            complaint.setResolutionNotes(resolutionNotes);
        }
        return mapToResponse(complaintRepository.save(complaint));
    }

    // ADMIN: view all complaints
    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ADMIN: assign complaint to agent
    public ComplaintResponse assignComplaint(Long complaintId, Long agentId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        complaint.setAgent(agent);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        return mapToResponse(complaintRepository.save(complaint));
    }

    // ADMIN: get all agents
    public List<User> getAllAgents() {
        return userRepository.findByRole(
                com.vamshi.complaint_management.enums.Role.AGENT);
    }
}
