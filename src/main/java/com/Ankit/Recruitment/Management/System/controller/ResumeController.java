package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.service.ProfileService;
import com.Ankit.Recruitment.Management.System.service.ResumeProcessingService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResumeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ResumeProcessingService resumeProcessingService;

    @PostMapping("/uploadResume")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file,
                                          Authentication authentication) {
        try {
            // Validate file type
            String contentType = file.getContentType();
            if (!"application/pdf".equals(contentType) &&
                    !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
                return ResponseEntity.badRequest().body("Only PDF and DOCX files are allowed");
            }

            String email = authentication.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get or create profile
            Profile profile = profileService.getProfileByUserId(user.getId())
                    .orElseGet(() -> profileService.createProfile(user));

            // Process resume with third-party API
            Map<String, Object> extractedData = resumeProcessingService.processResume(file);

            // Update profile with extracted data
            resumeProcessingService.updateProfileWithExtractedData(profile, extractedData);

            // Save file path (in real scenario, save file to storage and store path)
            profile.setResumeFileAddress("resumes/" + user.getId() + "_" + file.getOriginalFilename());

            Profile savedProfile = profileService.saveProfile(profile);

            return ResponseEntity.ok("Resume uploaded and processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading resume: " + e.getMessage());
        }
    }
}