
package com.vamshi.complaint_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private long totalComplaints;
    private long openComplaints;
    private long assignedComplaints;
    private long inProgressComplaints;
    private long resolvedComplaints;
    private long closedComplaints;
    private long totalUsers;
    private long totalAgents;
    private Map<String, Long> complaintsByCategory;
    private Map<String, Long> complaintsByPriority;
    private Map<String, Long> agentWorkload;
}