package com.vamshi.complaint_management.dto;

import com.vamshi.complaint_management.enums.Category;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private ComplaintStatus status;
    private String userName;
    private String userEmail;
    private String agentName;
    private String agentEmail;
    private String resolutionNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
