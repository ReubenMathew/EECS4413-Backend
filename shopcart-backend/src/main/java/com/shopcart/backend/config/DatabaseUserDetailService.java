package com.shopcart.backend.config;

import com.shopcart.backend.entity.User;
import com.shopcart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}