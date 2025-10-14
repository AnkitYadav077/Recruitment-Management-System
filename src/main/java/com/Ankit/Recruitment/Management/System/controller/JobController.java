package com.Ankit.Recruitment.Management.System.controller;

import com.Ankit.Recruitment.Management.System.dto.JobDto;
import com.Ankit.Recruitment.Management.System.entity.Job;
import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.payload.ApiResponse;
import com.Ankit.Recruitment.Management.System.service.JobService;
import com.Ankit.Recruitment.Management.System.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/admin/job")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobDto.CreateRequest createRequest,
                                       Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Job job = jobService.createJob(createRequest, user);
            JobDto.Response response = modelMapper.map(job, JobDto.Response.class);

            return ResponseEntity.ok(ApiResponse.success("Job created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/admin/job/{jobId}")
    public ResponseEntity<?> getJobWithApplicants(@PathVariable Long jobId, Authentication authentication) {
        try {
            Job job = jobService.getJobById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

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

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getAllJobs(Authentication authentication) {
        try {
            List<Job> jobs = jobService.getAllJobs();
            List<JobDto.Response> responses = jobs.stream()
                    .map(job -> modelMapper.map(job, JobDto.Response.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // ✅ FIXED: @RequestParam name specify kiya hai
    @GetMapping("/jobs/apply")
    public ResponseEntity<?> applyForJob(@RequestParam("job_id") Long jobId, Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            JobApplication application = jobService.applyForJob(jobId, user);

            return ResponseEntity.ok(ApiResponse.success("Successfully applied for the job", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}