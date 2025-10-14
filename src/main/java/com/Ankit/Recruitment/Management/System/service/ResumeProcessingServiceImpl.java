package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.service.ResumeProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeProcessingServiceImpl implements ResumeProcessingService {

    private static final String API_URL = "https://api.apilayer.com/resume_parser/upload";
    private static final String API_KEY = "0bWeisRWoLj3UdXt3MXMSMWptYFIpQfS";

    private final RestTemplate restTemplate;

    @Override
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

    @Override
    public void updateProfileWithExtractedData(Profile profile, Map<String, Object> extractedData) {
        Map<String, Runnable> updateActions = new HashMap<>();

        updateActions.put("name", () -> profile.setName(extractedData.get("name").toString()));
        updateActions.put("email", () -> profile.setEmail(extractedData.get("email").toString()));
        updateActions.put("phone", () -> profile.setPhone(extractedData.get("phone").toString()));
        updateActions.put("education", () -> profile.setEducation(extractedData.get("education").toString()));
        updateActions.put("experience", () -> profile.setExperience(extractedData.get("experience").toString()));

        updateActions.put("skills", () -> {
            Object skills = extractedData.get("skills");
            if (skills instanceof java.util.List) {
                profile.setSkills(String.join(", ", (java.util.List<String>) skills));
            } else {
                profile.setSkills(skills.toString());
            }
        });

        extractedData.keySet().forEach(key -> {
            if (updateActions.containsKey(key)) {
                updateActions.get(key).run();
            }
        });
    }
}