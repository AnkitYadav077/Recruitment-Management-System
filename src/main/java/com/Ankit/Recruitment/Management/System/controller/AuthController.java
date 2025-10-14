package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.security.JwtUtil;
import com.Ankit.Recruitment.Management.System.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto.SignupRequest signupRequest) {
        try {
            User user = userService.createUser(signupRequest);
            UserDto.UserResponse userResponse = modelMapper.map(user, UserDto.UserResponse.class);

            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDto.LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            User user = userService.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDto.UserResponse userResponse = modelMapper.map(user, UserDto.UserResponse.class);
            UserDto.LoginResponse loginResponse = new UserDto.LoginResponse(jwt, userResponse);

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}
