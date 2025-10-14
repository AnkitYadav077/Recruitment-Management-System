package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.ProfileDto;
import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.service.ProfileService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/applicants")
    public ResponseEntity<?> getAllApplicants() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto.UserResponse> responses = users.stream()
                    .map(user -> modelMapper.map(user, UserDto.UserResponse.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<?> getApplicantProfile(@PathVariable Long applicantId) {
        try {
            Profile profile = profileService.getProfileByUserId(applicantId)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));

            ProfileDto.Response response = modelMapper.map(profile, ProfileDto.Response.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}