package com.Ankit.Recruitment.Management.System.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_id", "applicant_id"})
})
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(nullable = false)
    private LocalDateTime appliedDate;

    @PrePersist
    protected void onCreate() {
        appliedDate = LocalDateTime.now();
    }

    // Constructors
    public JobApplication() {}

    public JobApplication(Job job, User applicant) {
        this.job = job;
        this.applicant = applicant;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public User getApplicant() { return applicant; }
    public void setApplicant(User applicant) { this.applicant = applicant; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }
}
