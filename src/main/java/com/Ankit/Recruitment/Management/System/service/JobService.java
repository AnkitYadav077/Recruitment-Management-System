package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.dto.JobDto;
import com.Ankit.Recruitment.Management.System.entity.Job;
import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import com.Ankit.Recruitment.Management.System.entity.User;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job createJob(JobDto.CreateRequest createRequest, User postedBy);
    List<Job> getAllJobs();
    Optional<Job> getJobById(Long id);
    List<Job> getJobsByUser(Long userId);
    JobApplication applyForJob(Long jobId, User applicant);
    List<JobApplication> getJobApplications(Long jobId);
}