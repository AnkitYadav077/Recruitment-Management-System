package com.Ankit.Recruitment.Management.System.dto;

import com.Ankit.Recruitment.Management.System.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupRequest {
        @NotBlank
        private String name;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        private UserType userType;
        private String profileHeadline;
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String type = "Bearer";
        private UserResponse user;

        public LoginResponse(String token, UserResponse user) {
            this.token = token;
            this.user = user;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        private UserType userType;
        private String profileHeadline;
        private String address;
    }
}