package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(User user) {
        Profile profile = new Profile(user);
        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public boolean profileExistsForUser(Long userId) {
        return profileRepository.existsByUserId(userId);
    }
}