package com.lucasvieiras.blog_api.services;

import com.lucasvieiras.blog_api.dto.requests.auth.AuthResponse;
import com.lucasvieiras.blog_api.dto.requests.auth.LoginRequest;
import com.lucasvieiras.blog_api.dto.requests.auth.RegisterRequest;
import com.lucasvieiras.blog_api.entities.User;
import com.lucasvieiras.blog_api.enums.Role;
import com.lucasvieiras.blog_api.exceptions.ConflictException;
import com.lucasvieiras.blog_api.repositories.UserRepository;
import com.lucasvieiras.blog_api.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("User", "username", request.username());
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("User", "email", request.email());
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .hashedPassword(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return login(new LoginRequest(user.getUsername(), user.getHashedPassword()));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(loginRequest.username()).orElseThrow();

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
};
