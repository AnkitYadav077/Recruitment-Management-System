package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.JobDto;
import com.Ankit.Recruitment.Management.System.entity.Job;
import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.service.JobService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/admin/job")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobDto.CreateRequest createRequest,
                                       Authentication authentication) {
        try {
            String email = authentication.getName();
            log.info("Creating new job by user: {}", email);

            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Job job = jobService.createJob(createRequest, user);
            JobDto.Response response = modelMapper.map(job, JobDto.Response.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating job: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/job/{jobId}")
    public ResponseEntity<?> getJobWithApplicants(@PathVariable Long jobId, Authentication authentication) {
        try {
            log.info("Fetching job with applicants for job ID: {}", jobId);

            Job job = jobService.getJobById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

            List<JobApplication> applications = jobService.getJobApplications(jobId);

            JobDto.DetailedResponse response = modelMapper.map(job, JobDto.DetailedResponse.class);
            response.setApplicants(applications.stream()
                    .map(app -> {
                        JobDto.ApplicantResponse applicantResponse = new JobDto.ApplicantResponse();
                        applicantResponse.setId(app.getApplicant().getId());
                        applicantResponse.setName(app.getApplicant().getName());
                        applicantResponse.setEmail(app.getApplicant().getEmail());
                        applicantResponse.setAppliedDate(app.getAppliedDate());
                        return applicantResponse;
                    })
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching job with applicants: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getAllJobs(Authentication authentication) {
        try {
            log.info("Fetching all jobs for user: {}", authentication.getName());

            List<Job> jobs = jobService.getAllJobs();
            List<JobDto.Response> responses = jobs.stream()
                    .map(job -> modelMapper.map(job, JobDto.Response.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error fetching jobs: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs/apply")
    public ResponseEntity<?> applyForJob(@RequestParam("job_id") Long jobId, Authentication authentication) {
        try {
            String email = authentication.getName();
            log.info("User {} applying for job ID: {}", email, jobId);

            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            jobService.applyForJob(jobId, user);

            return ResponseEntity.ok("Successfully applied for the job");
        } catch (Exception e) {
            log.error("Error applying for job: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}