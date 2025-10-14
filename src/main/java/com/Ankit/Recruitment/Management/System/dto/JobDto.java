package com.Ankit.Recruitment.Management.System.dto;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class JobDto {
    public static class CreateRequest {
        @NotBlank
        private String title;

        private String description;
        private String companyName;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
    }

    public static class Response {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime postedOn;
        private Integer totalApplications;
        private String companyName;
        private UserDto.UserResponse postedBy;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getPostedOn() { return postedOn; }
        public void setPostedOn(LocalDateTime postedOn) { this.postedOn = postedOn; }

        public Integer getTotalApplications() { return totalApplications; }
        public void setTotalApplications(Integer totalApplications) { this.totalApplications = totalApplications; }

        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }

        public UserDto.UserResponse getPostedBy() { return postedBy; }
        public void setPostedBy(UserDto.UserResponse postedBy) { this.postedBy = postedBy; }
    }

    public static class DetailedResponse {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime postedOn;
        private Integer totalApplications;
        private String companyName;
        private UserDto.UserResponse postedBy;
        private List<ApplicantResponse> applicants;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getPostedOn() { return postedOn; }
        public void setPostedOn(LocalDateTime postedOn) { this.postedOn = postedOn; }

        public Integer getTotalApplications() { return totalApplications; }
        public void setTotalApplications(Integer totalApplications) { this.totalApplications = totalApplications; }

        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }

        public UserDto.UserResponse getPostedBy() { return postedBy; }
        public void setPostedBy(UserDto.UserResponse postedBy) { this.postedBy = postedBy; }

        public List<ApplicantResponse> getApplicants() { return applicants; }
        public void setApplicants(List<ApplicantResponse> applicants) { this.applicants = applicants; }
    }

    public static class ApplicantResponse {
        private Long id;
        private String name;
        private String email;
        private LocalDateTime appliedDate;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public LocalDateTime getAppliedDate() { return appliedDate; }
        public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }
    }
}