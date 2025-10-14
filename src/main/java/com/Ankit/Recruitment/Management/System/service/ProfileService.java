package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.entity.Profile;
import com.Ankit.Recruitment.Management.System.entity.User;

import java.util.Optional;

public interface ProfileService {
    Profile createProfile(User user);
    Optional<Profile> getProfileByUserId(Long userId);
    Profile saveProfile(Profile profile);
    boolean profileExistsForUser(Long userId);
}