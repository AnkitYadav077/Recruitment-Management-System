package com.Ankit.Recruitment.Management.System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class JobDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank
        private String title;

        private String description;
        private String companyName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime postedOn;
        private Integer totalApplications;
        private String companyName;
        private UserDto.UserResponse postedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailedResponse {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime postedOn;
        private Integer totalApplications;
        private String companyName;
        private UserDto.UserResponse postedBy;
        private List<ApplicantResponse> applicants;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicantResponse {
        private Long id;
        private String name;
        private String email;
        private LocalDateTime appliedDate;
    }
}