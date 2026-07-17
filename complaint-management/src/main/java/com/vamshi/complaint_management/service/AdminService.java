package com.vamshi.complaint_management.service;

import com.vamshi.complaint_management.dto.AuthResponse;
import com.vamshi.complaint_management.dto.LoginHistoryResponse;
import com.vamshi.complaint_management.entity.LoginHistory;
import com.vamshi.complaint_management.entity.User;
import com.vamshi.complaint_management.enums.Role;
import com.vamshi.complaint_management.exception.ResourceNotFoundException;
import com.vamshi.complaint_management.repository.LoginHistoryRepository;
import com.vamshi.complaint_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    private LoginHistoryResponse mapToLoginHistoryResponse(LoginHistory lh) {
        return LoginHistoryResponse.builder()
                .id(lh.getId())
                .userName(lh.getUser().getName())
                .userEmail(lh.getUser().getEmail())
                .userRole(lh.getUser().getRole().name())
                .loginTime(lh.getLoginTime())
                .logoutTime(lh.getLogoutTime())
                .ipAddress(lh.getIpAddress())
                .status(lh.getStatus())
                .build();
    }

    // Get all users
    public List<AuthResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> AuthResponse.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .role(user.getRole().name())
                        .build())
                .collect(Collectors.toList());
    }

    // Create agent account
    public AuthResponse createAgent(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        User agent = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.AGENT)
                .build();
        userRepository.save(agent);
        return AuthResponse.builder()
                .email(agent.getEmail())
                .name(agent.getName())
                .role(agent.getRole().name())
                .message("Agent created successfully")
                .build();
    }

    // Disable user account
    public String disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));
        user.setEnabled(false);
        userRepository.save(user);
        return "User " + user.getName() + " has been disabled";
    }

    // Enable user account
    public String enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));
        user.setEnabled(true);
        userRepository.save(user);
        return "User " + user.getName() + " has been enabled";
    }

    // Get all login history
    public List<LoginHistoryResponse> getAllLoginHistory() {
        return loginHistoryRepository.findAllByOrderByLoginTimeDesc()
                .stream()
                .map(this::mapToLoginHistoryResponse)
                .collect(Collectors.toList());
    }

    // Get login history by user
    public List<LoginHistoryResponse> getUserLoginHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));
        return loginHistoryRepository.findByUser(user)
                .stream()
                .map(this::mapToLoginHistoryResponse)
                .collect(Collectors.toList());
    }
}