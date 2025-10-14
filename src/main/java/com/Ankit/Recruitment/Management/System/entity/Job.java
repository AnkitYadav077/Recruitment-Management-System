package com.Ankit.Recruitment.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime postedOn;

    @Column(name = "total_applications")
    private Integer totalApplications = 0;

    @Column(name = "company_name")
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        if (postedOn == null) {
            postedOn = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Job() {}

    public Job(String title, String description, String companyName, User postedBy) {
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.postedBy = postedBy;
    }

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

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }

    public List<JobApplication> getApplications() { return applications; }
    public void setApplications(List<JobApplication> applications) { this.applications = applications; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}