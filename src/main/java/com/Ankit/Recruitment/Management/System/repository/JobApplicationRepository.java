package com.Ankit.Recruitment.Management.System.repository;



import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByJobIdAndApplicantId(Long jobId, Long applicantId);
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByApplicantId(Long applicantId);
    Boolean existsByJobIdAndApplicantId(Long jobId, Long applicantId);

    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.job.id = :jobId")
    Long countApplicationsByJobId(@Param("jobId") Long jobId);
}