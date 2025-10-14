package com.Ankit.Recruitment.Management.System.dto;


public class ProfileDto {
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

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getResumeFileAddress() { return resumeFileAddress; }
        public void setResumeFileAddress(String resumeFileAddress) { this.resumeFileAddress = resumeFileAddress; }

        public String getSkills() { return skills; }
        public void setSkills(String skills) { this.skills = skills; }

        public String getEducation() { return education; }
        public void setEducation(String education) { this.education = education; }

        public String getExperience() { return experience; }
        public void setExperience(String experience) { this.experience = experience; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public UserDto.UserResponse getUser() { return user; }
        public void setUser(UserDto.UserResponse user) { this.user = user; }
    }
}
