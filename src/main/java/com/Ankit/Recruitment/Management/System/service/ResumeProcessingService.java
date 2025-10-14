package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResumeProcessingService {

    private static final String API_URL = "https://api.apilayer.com/resume_parser/upload";
    private static final String API_KEY = "0bWeisRWoLj3UdXt3MXMSMWptYFIpQfS";

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> processResume(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", API_KEY);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to process resume");
        }
    }

    public void updateProfileWithExtractedData(Profile profile, Map<String, Object> extractedData) {
        if (extractedData.containsKey("name")) {
            profile.setName(extractedData.get("name").toString());
        }

        if (extractedData.containsKey("email")) {
            profile.setEmail(extractedData.get("email").toString());
        }

        if (extractedData.containsKey("phone")) {
            profile.setPhone(extractedData.get("phone").toString());
        }

        if (extractedData.containsKey("skills")) {
            Object skills = extractedData.get("skills");
            if (skills instanceof java.util.List) {
                profile.setSkills(String.join(", ", (java.util.List<String>) skills));
            } else {
                profile.setSkills(skills.toString());
            }
        }

        if (extractedData.containsKey("education")) {
            Object education = extractedData.get("education");
            profile.setEducation(education.toString());
        }

        if (extractedData.containsKey("experience")) {
            Object experience = extractedData.get("experience");
            profile.setExperience(experience.toString());
        }
    }
}
