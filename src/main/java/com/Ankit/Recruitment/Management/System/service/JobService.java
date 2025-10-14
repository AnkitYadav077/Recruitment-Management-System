package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.dto.JobDto;
import com.Ankit.Recruitment.Management.System.entity.Job;
import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.repository.JobApplicationRepository;
import com.Ankit.Recruitment.Management.System.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public Job createJob(JobDto.CreateRequest createRequest, User postedBy) {
        Job job = new Job();
        job.setTitle(createRequest.getTitle());
        job.setDescription(createRequest.getDescription());
        job.setCompanyName(createRequest.getCompanyName());
        job.setPostedBy(postedBy);

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAllOrderByPostedOnDesc();
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public List<Job> getJobsByUser(Long userId) {
        return jobRepository.findByPostedById(userId);
    }

    @Transactional
    public JobApplication applyForJob(Long jobId, User applicant) {
        // Check if already applied
        if (jobApplicationRepository.existsByJobIdAndApplicantId(jobId, applicant.getId())) {
            throw new RuntimeException("You have already applied for this job");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplication application = new JobApplication(job, applicant);
        JobApplication savedApplication = jobApplicationRepository.save(application);

        // Update total applications count
        Long applicationCount = jobApplicationRepository.countApplicationsByJobId(jobId);
        job.setTotalApplications(applicationCount.intValue());
        jobRepository.save(job);

        return savedApplication;
    }

    public List<JobApplication> getJobApplications(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }
}