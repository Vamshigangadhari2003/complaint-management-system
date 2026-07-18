package com.vamshi.complaint_management.service;

import com.vamshi.complaint_management.config.UserDetailsImpl;
import com.vamshi.complaint_management.dto.AuthResponse;
import com.vamshi.complaint_management.dto.LoginRequest;
import com.vamshi.complaint_management.dto.RegisterRequest;
import com.vamshi.complaint_management.entity.LoginHistory;
import com.vamshi.complaint_management.entity.User;
import com.vamshi.complaint_management.enums.Role;
import com.vamshi.complaint_management.repository.LoginHistoryRepository;
import com.vamshi.complaint_management.repository.UserRepository;
import com.vamshi.complaint_management.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userDetails.getUser();
            String token = jwtUtil.generateToken(userDetails);

            // Save login history
            LoginHistory loginHistory = LoginHistory.builder()
                    .user(user)
                    .ipAddress(httpRequest.getRemoteAddr())
                    .build();
            loginHistoryRepository.save(loginHistory);

            return AuthResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole().name())
                    .message("Login successful")
                    .build();
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUsername(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            loginHistoryRepository.findByUserAndStatus(user, "ACTIVE")
                    .ifPresent(loginHistory -> {
                        loginHistory.setStatus("LOGGED_OUT");
                        loginHistory.setLogoutTime(java.time.LocalDateTime.now());
                        loginHistoryRepository.save(loginHistory);
                    });
        }
    }
}
