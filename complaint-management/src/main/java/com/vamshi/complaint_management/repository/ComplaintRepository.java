package com.vamshi.complaint_management.repository;

import com.vamshi.complaint_management.entity.Complaint;
import com.vamshi.complaint_management.entity.User;
import com.vamshi.complaint_management.enums.ComplaintStatus;
import com.vamshi.complaint_management.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{
    List<Complaint> findByUser(User user);
    List<Complaint> findByAgent(User agent);
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByCategory(Category category);
    List<Complaint> findByUserAndStatus(User user, ComplaintStatus status);
}
