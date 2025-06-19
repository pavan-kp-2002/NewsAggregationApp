package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.LoginRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupResponseDTO;
import com.learnandcode.news_aggregator.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponseDTO> signUp(@RequestBody SignupRequestDTO userDto){
        return ResponseEntity.ok(authenticationService.signUp(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDto));
    }

}
