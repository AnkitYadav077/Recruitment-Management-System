package com.Ankit.Recruitment.Management.System.repository;

import com.Ankit.Recruitment.Management.System.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByPostedById(Long postedById);

    @Query("SELECT j FROM Job j ORDER BY j.postedOn DESC")
    List<Job> findAllOrderByPostedOnDesc();
}