package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.ProfileDto;
import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.service.ProfileService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    @GetMapping("/applicants")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllApplicants() {
        try {
            log.info("Fetching all applicants");
            List<User> users = userService.getAllUsers();

            List<UserDto.UserResponse> responses = users.stream()
                    .map(user -> modelMapper.map(user, UserDto.UserResponse.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error fetching applicants: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/applicant/{applicantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getApplicantProfile(@PathVariable Long applicantId) {
        try {
            log.info("Fetching profile for applicant ID: {}", applicantId);

            Profile profile = profileService.getProfileByUserId(applicantId)
                    .orElseThrow(() -> new RuntimeException("Profile not found for applicant ID: " + applicantId));

            ProfileDto.Response response = modelMapper.map(profile, ProfileDto.Response.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching applicant profile: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}