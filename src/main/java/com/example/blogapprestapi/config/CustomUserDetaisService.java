package com.example.blogapprestapi.config;

import com.example.blogapprestapi.entities.User;
import com.example.blogapprestapi.exceptions.ResourceNotFoundException;
import com.example.blogapprestapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetaisService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(username);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User","Username : "+username,0);
        }
        return user.get();
    }
}
