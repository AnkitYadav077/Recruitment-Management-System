package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.security.JwtUtil;
import com.Ankit.Recruitment.Management.System.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto.SignupRequest signupRequest) {
        try {
            log.info("Registering new user with email: {}", signupRequest.getEmail());

            User user = userService.createUser(signupRequest);
            UserDto.UserResponse userResponse = modelMapper.map(user, UserDto.UserResponse.class);

            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException e) {
            log.error("Error during user registration: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDto.LoginRequest loginRequest) {
        try {
            log.info("Login attempt for email: {}", loginRequest.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            User user = userService.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDto.UserResponse userResponse = modelMapper.map(user, UserDto.UserResponse.class);
            UserDto.LoginResponse loginResponse = new UserDto.LoginResponse(jwt, userResponse);

            log.info("Login successful for user: {}", loginRequest.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("Login failed for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}