package com.vamshi.complaint_management.service;

import com.vamshi.complaint_management.dto.DashboardResponse;
import com.vamshi.complaint_management.entity.Complaint;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.enums.Role;
import com.vamshi.complaint_management.repository.ComplaintRepository;
import com.vamshi.complaint_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public DashboardResponse getDashboard() {
        List<Complaint> allComplaints = complaintRepository.findAll();

        // Count by status
        long total = allComplaints.size();
        long open = allComplaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.OPEN).count();
        long assigned = allComplaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.ASSIGNED).count();
        long inProgress = allComplaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.IN_PROGRESS).count();
        long resolved = allComplaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.RESOLVED).count();
        long closed = allComplaints.stream()
                .filter(c -> c.getStatus() == ComplaintStatus.CLOSED).count();

        // Count users and agents
        long totalUsers = userRepository.findByRole(Role.USER).size();
        long totalAgents = userRepository.findByRole(Role.AGENT).size();

        // Complaints by category
        Map<String, Long> byCategory = allComplaints.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getCategory().name(), Collectors.counting()));

        // Complaints by priority
        Map<String, Long> byPriority = allComplaints.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getPriority().name(), Collectors.counting()));

        // Agent workload
        Map<String, Long> agentWorkload = allComplaints.stream()
                .filter(c -> c.getAgent() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getAgent().getName(), Collectors.counting()));

        return DashboardResponse.builder()
                .totalComplaints(total)
                .openComplaints(open)
                .assignedComplaints(assigned)
                .inProgressComplaints(inProgress)
                .resolvedComplaints(resolved)
                .closedComplaints(closed)
                .totalUsers(totalUsers)
                .totalAgents(totalAgents)
                .complaintsByCategory(byCategory)
                .complaintsByPriority(byPriority)
                .agentWorkload(agentWorkload)
                .build();
    }
}