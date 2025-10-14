package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.entity.UserType;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserDto.SignupRequest signupRequest);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByType(UserType userType);
    Optional<User> getUserById(Long id);
    boolean existsByEmail(String email);
}