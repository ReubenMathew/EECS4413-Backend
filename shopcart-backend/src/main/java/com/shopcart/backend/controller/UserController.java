package com.shopcart.backend.controller;

import com.shopcart.backend.entity.AuthRequest;
import com.shopcart.backend.entity.RegistrationRequest;
import com.shopcart.backend.entity.User;
import com.shopcart.backend.repository.UserRepository;
import com.shopcart.backend.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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


    @PostMapping(value = "/authenticate")
    public ResponseEntity<Map> generateToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        HashMap<String, String> responseMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            responseMap.put("token", jwtUtils.generateToken(authRequest.getUserName()));
        } catch (UsernameNotFoundException e) {
            responseMap.put("error", "username not found");
            status = HttpStatus.FORBIDDEN;
        } catch (BadCredentialsException e) {
            responseMap.put("error", "invalid username/password");
            status = HttpStatus.FORBIDDEN;
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", "authentication error");
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(responseMap, status);
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return new ArrayList<>(userRepository.findAll());
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
