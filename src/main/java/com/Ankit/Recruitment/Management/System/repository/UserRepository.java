package com.Ankit.Recruitment.Management.System.repository;

import com.Ankit.Recruitment.Management.System.entity.User;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findByUserType(com.Ankit.Recruitment.Management.System.entity.UserType userType);
}