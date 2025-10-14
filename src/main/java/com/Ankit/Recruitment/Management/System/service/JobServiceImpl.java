package com.Ankit.Recruitment.Management.System.service.impl;

import com.Ankit.Recruitment.Management.System.dto.JobDto;
import com.Ankit.Recruitment.Management.System.entity.Job;
import com.Ankit.Recruitment.Management.System.entity.JobApplication;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.repository.JobApplicationRepository;
import com.Ankit.Recruitment.Management.System.repository.JobRepository;
import com.Ankit.Recruitment.Management.System.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Job createJob(JobDto.CreateRequest createRequest, User postedBy) {
        Job job = modelMapper.map(createRequest, Job.class);
        job.setPostedBy(postedBy);
        return jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAllOrderByPostedOnDesc();
    }

    @Override
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public List<Job> getJobsByUser(Long userId) {
        return jobRepository.findByPostedById(userId);
    }

    @Override
    public JobApplication applyForJob(Long jobId, User applicant) {
        if (jobApplicationRepository.existsByJobIdAndApplicantId(jobId, applicant.getId())) {
            throw new RuntimeException("You have already applied for this job");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplication application = new JobApplication(job, applicant);
        JobApplication savedApplication = jobApplicationRepository.save(application);

        job.setTotalApplications(jobApplicationRepository.countApplicationsByJobId(jobId).intValue());
        jobRepository.save(job);

        return savedApplication;
    }

    @Override
    public List<JobApplication> getJobApplications(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }
}