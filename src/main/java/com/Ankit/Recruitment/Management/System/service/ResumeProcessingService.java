package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ResumeProcessingService {
    Map<String, Object> processResume(MultipartFile file) throws IOException;
    void updateProfileWithExtractedData(Profile profile, Map<String, Object> extractedData);
}