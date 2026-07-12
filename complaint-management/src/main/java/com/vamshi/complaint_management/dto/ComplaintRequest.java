package com.vamshi.complaint_management.dto;

import com.vamshi.complaint_management.enums.Category;
import com.vamshi.complaint_management.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComplaintRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Priority is required")
    private Priority priority;
}
