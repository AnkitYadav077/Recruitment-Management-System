package com.Ankit.Recruitment.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    private String profileHeadline;
    private String address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
    private List<Job> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<JobApplication> applications = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public User() {}

    public User(String name, String email, String passwordHash, UserType userType) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public String getProfileHeadline() { return profileHeadline; }
    public void setProfileHeadline(String profileHeadline) { this.profileHeadline = profileHeadline; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public List<Job> getJobs() { return jobs; }
    public void setJobs(List<Job> jobs) { this.jobs = jobs; }

    public List<JobApplication> getApplications() { return applications; }
    public void setApplications(List<JobApplication> applications) { this.applications = applications; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
