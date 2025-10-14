package com.Ankit.Recruitment.Management.System.service;

import com.Ankit.Recruitment.Management.System.dto.UserDto;
import com.Ankit.Recruitment.Management.System.entity.User;
import com.Ankit.Recruitment.Management.System.entity.UserType; // ✅ Correct import
import com.Ankit.Recruitment.Management.System.repository.UserRepository;
// ❌ Remove this: import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDto.SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserType(signupRequest.getUserType() != null ? signupRequest.getUserType() : UserType.APPLICANT);
        user.setProfileHeadline(signupRequest.getProfileHeadline());
        user.setAddress(signupRequest.getAddress());

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}