package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.LoginRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupRequestDTO;
import com.learnandcode.news_aggregator.dto.SignupResponseDTO;

public interface AuthenticationService {
     SignupResponseDTO signUp(SignupRequestDTO userDTO);
     String login(LoginRequestDTO loginRequestDTO);

}
