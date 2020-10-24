package com.auth.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.resource.model.Roles;
import com.auth.resource.model.UserProfile;
import com.auth.resource.repository.RoleRepository;
import com.auth.resource.repository.UserRepository;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;
   
    @Autowired
    private RoleRepository roleRepository;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(UserProfile user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        user.setEnabled(true);
        userRepository.save(user);
        roleRepository.save(new Roles(user.getUsername(),"USER"));
    }

    public UserProfile findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}