package com.auth.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.resource.model.UserProfile;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
}
