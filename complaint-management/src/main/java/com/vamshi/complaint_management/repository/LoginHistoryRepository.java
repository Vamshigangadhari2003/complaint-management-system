package com.vamshi.complaint_management.repository;

import com.vamshi.complaint_management.entity.LoginHistory;
import com.vamshi.complaint_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByUser(User user);
    Optional<LoginHistory> findByUserAndStatus(User user, String status);
    List<LoginHistory> findAllByOrderByLoginTimeDesc();
}
