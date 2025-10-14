package com.Ankit.Recruitment.Management.System.dto;

import lombok.Data;

@Data
public class ProfileDto {

    @Data
    public static class Response {
        private Long id;
        private String resumeFileAddress;
        private String skills;
        private String education;
        private String experience;
        private String name;
        private String email;
        private String phone;
        private UserDto.UserResponse user;
    }
}