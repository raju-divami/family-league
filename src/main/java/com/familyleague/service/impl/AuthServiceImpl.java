package com.familyleague.service.impl;

import com.familyleague.config.JwtProperties;
import com.familyleague.dto.request.LoginRequest;
import com.familyleague.dto.request.RegisterRequest;
import com.familyleague.dto.response.AuthResponse;
import com.familyleague.entity.Role;
import com.familyleague.entity.User;
import com.familyleague.entity.UserRole;
import com.familyleague.exception.BusinessException;
import com.familyleague.exception.ConflictException;
import com.familyleague.repository.RoleRepository;
import com.familyleague.repository.UserRepository;
import com.familyleague.repository.UserRoleRepository;
import com.familyleague.security.JwtTokenProvider;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider tokenProvider,
                           JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.jwtProperties = jwtProperties;
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.debug("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new BusinessException("Account is not active");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        UserPrincipal principal = createUserPrincipal(user);
        String accessToken = tokenProvider.generateToken(principal);
        String refreshToken = tokenProvider.generateRefreshToken(principal);

        log.info("User logged in successfully: {}", user.getEmail());
        return AuthResponse.of(accessToken, refreshToken, jwtProperties.getExpirationMs() / 1000);
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.debug("Registration attempt for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .provider("LOCAL")
                .status("ACTIVE")
                .build();

        user = userRepository.save(user);

        Role userRole = roleRepository.findByCode("USER")
                .orElseThrow(() -> new BusinessException("Default USER role not found"));

        UserRole userRoleAssignment = UserRole.builder()
                .user(user)
                .role(userRole)
                .build();
        userRoleRepository.save(userRoleAssignment);

        UserPrincipal principal = createUserPrincipal(user);
        String accessToken = tokenProvider.generateToken(principal);
        String refreshToken = tokenProvider.generateRefreshToken(principal);

        log.info("User registered successfully: {}", user.getEmail());
        return AuthResponse.of(accessToken, refreshToken, jwtProperties.getExpirationMs() / 1000);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        log.debug("Token refresh attempt");

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        Long userId = tokenProvider.extractUserId(refreshToken);
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        UserPrincipal principal = createUserPrincipal(user);
        String newAccessToken = tokenProvider.generateToken(principal);
        String newRefreshToken = tokenProvider.generateRefreshToken(principal);

        return AuthResponse.of(newAccessToken, newRefreshToken, jwtProperties.getExpirationMs() / 1000);
    }

    private UserPrincipal createUserPrincipal(User user) {
        Set<String> roleCodes = userRoleRepository.findRoleCodesByUserId(user.getId());
        return UserPrincipal.from(user, roleCodes);
    }
}
