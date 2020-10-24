package com.auth.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.resource.model.UserProfile;
import com.auth.resource.repository.UserRepository;

@Service
public class UserDetailsServiceImpl{
    @Autowired
    private UserRepository userRepository;

   
    public UserProfile loadUserByUsername(String username) {
        UserProfile user = userRepository.findByUsername(username);
        return user;
    }
}
