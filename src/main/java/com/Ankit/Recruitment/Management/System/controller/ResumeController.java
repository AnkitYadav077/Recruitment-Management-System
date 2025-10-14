package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.service.ProfileService;
import com.Ankit.Recruitment.Management.System.service.ResumeProcessingService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResumeController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ResumeProcessingService resumeProcessingService;

    @PostMapping("/uploadResume")
    @PreAuthorize("hasAuthority('APPLICANT')")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file,
                                          Authentication authentication) {
        try {
            log.info("Resume upload request received");

            String contentType = file.getContentType();
            if (!"application/pdf".equals(contentType) &&
                    !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
                log.warn("Invalid file type attempted: {}", contentType);
                return ResponseEntity.badRequest().body("Only PDF and DOCX files are allowed");
            }

            String email = authentication.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Profile profile = profileService.getProfileByUserId(user.getId())
                    .orElseGet(() -> profileService.createProfile(user));

            Map<String, Object> extractedData = resumeProcessingService.processResume(file);

            resumeProcessingService.updateProfileWithExtractedData(profile, extractedData);

            profile.setResumeFileAddress("resumes/" + user.getId() + "_" + file.getOriginalFilename());

            profileService.saveProfile(profile);

            log.info("Resume processed successfully for user: {}", email);
            return ResponseEntity.ok("Resume uploaded and processed successfully");
        } catch (Exception e) {
            log.error("Error uploading resume: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error uploading resume: " + e.getMessage());
        }
    }
}