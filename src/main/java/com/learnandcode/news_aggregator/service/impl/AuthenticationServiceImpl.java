package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.LoginRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupResponseDTO;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserRole;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.AuthenticationService;
import com.learnandcode.news_aggregator.service.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

@Autowired
    JwtUtils jwtUtils;

    @Override
    public SignupResponseDTO signUp(SignupRequestDTO userDTO) {
        if(!StringUtils.hasText(userDTO.getUsername()) || userDTO.getUsername().length() < 4){
            throw new IllegalArgumentException("Username must be at least 4 characters long.");
        }

        if (!userDTO.getUsername().matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Username must contain only letters.");
        }

        if(!StringUtils.hasText(userDTO.getEmail()) || !userDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new IllegalArgumentException("Invalid email format.");
        }

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        if(!StringUtils.hasText(userDTO.getPassword())|| userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 12){
            throw new IllegalArgumentException("Password must be between 6 and 12 characters long.");
        }


        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setUserRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        return new SignupResponseDTO("User registered successfully", user.getUsername(), user.getEmail());
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO){
        if(!StringUtils.hasText(loginRequestDTO.getEmail()) || !loginRequestDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new IllegalArgumentException("Invalid email format.");
        }

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + loginRequestDTO.getEmail()));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Invalid password.");
        }

        return jwtUtils.generateToken(user.getUsername(), user.getUserRole().toString());
    }

}
