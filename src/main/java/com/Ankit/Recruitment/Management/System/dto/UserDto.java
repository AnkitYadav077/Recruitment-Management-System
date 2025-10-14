package com.Ankit.Recruitment.Management.System.dto;

import com.Ankit.Recruitment.Management.System.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

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

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public UserType getUserType() { return userType; }
        public void setUserType(UserType userType) { this.userType = userType; }

        public String getProfileHeadline() { return profileHeadline; }
        public void setProfileHeadline(String profileHeadline) { this.profileHeadline = profileHeadline; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String type = "Bearer";
        private UserResponse user;

        public LoginResponse(String token, UserResponse user) {
            this.token = token;
            this.user = user;
        }

        // Getters and Setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public UserResponse getUser() { return user; }
        public void setUser(UserResponse user) { this.user = user; }
    }

    // ✅ YEH MISSING THA - UserResponse class
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        private UserType userType;
        private String profileHeadline;
        private String address;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public UserType getUserType() { return userType; }
        public void setUserType(UserType userType) { this.userType = userType; }

        public String getProfileHeadline() { return profileHeadline; }
        public void setProfileHeadline(String profileHeadline) { this.profileHeadline = profileHeadline; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
}