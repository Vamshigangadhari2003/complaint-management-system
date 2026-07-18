package com.vamshi.complaint_management.repository;

import com.vamshi.complaint_management.entity.Complaint;
import com.vamshi.complaint_management.entity.User;
import com.vamshi.complaint_management.enums.Category;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user);
    List<Complaint> findByAgent(User agent);
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByCategory(Category category);
    List<Complaint> findByPriority(Priority priority);
    List<Complaint> findByUserAndStatus(User user, ComplaintStatus status);

    @Query("SELECT c FROM Complaint c WHERE " +
            "(:status IS NULL OR c.status = :status) AND " +
            "(:category IS NULL OR c.category = :category) AND " +
            "(:priority IS NULL OR c.priority = :priority)")
    List<Complaint> searchComplaints(
            @Param("status") ComplaintStatus status,
            @Param("category") Category category,
            @Param("priority") Priority priority);
}