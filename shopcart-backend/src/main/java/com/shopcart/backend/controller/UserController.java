package com.shopcart.backend.controller;

import com.shopcart.backend.entity.AuthRequest;
import com.shopcart.backend.entity.RegistrationRequest;
import com.shopcart.backend.entity.User;
import com.shopcart.backend.repository.UserRepository;
import com.shopcart.backend.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/authenticate")
    public String generateToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (UsernameNotFoundException e) {
            throw new Exception("Username not found");
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username/password");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Authentication error");
        }
        return jwtUtils.generateToken(authRequest.getUserName());
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegistrationRequest registrationUser) throws Exception {
        if (!userRepository.findUserByUsername(registrationUser.getUserName()).isEmpty()) {
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setUsername(registrationUser.getUserName());
        user.setEmail(registrationUser.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
        user.setRoleCode("USER");
        userRepository.save(user);
        return userRepository.findUserByUsername(user.getUsername()).get();
    }
}
