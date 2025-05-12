package Telemedcine.cwa.telemedcine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.config.UserDetailsImpl;
import Telemedcine.cwa.telemedcine.model.User;
import Telemedcine.cwa.telemedcine.repositories.UserRepository;
@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new UserDetailsImpl(user); // Return your custom UserDetailsImpl
}}