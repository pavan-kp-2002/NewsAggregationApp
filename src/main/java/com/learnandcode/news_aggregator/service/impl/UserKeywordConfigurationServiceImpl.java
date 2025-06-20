package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.NotificationConfigurationStatus;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserKeywordConfiguration;
import com.learnandcode.news_aggregator.repositories.UserKeywordConfigurationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.UserKeywordConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserKeywordConfigurationServiceImpl implements UserKeywordConfigurationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserKeywordConfigurationRepository userKeywordConfigurationRepository;
    @Override
    public List<UserKeywordConfiguration> getUserKeywordConfigurations() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);

        if (userOpted.isPresent()) {
            return userKeywordConfigurationRepository.findAllByUser(userOpted.get());
        } else {
            throw new UserNotFoundException("User with the given username does not exist " + userName);
        }
    }

    public void addKeywordForUser(String keyword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(!userOpted.isPresent()){
            throw new UserNotFoundException("User with the given username does not exist "+ userName);
        }

        boolean exists = userKeywordConfigurationRepository.existsByUserAndKeyword(userOpted.get(), keyword);
        if(exists){
            throw new IllegalArgumentException("Keyword already exists for this user");
        }

        UserKeywordConfiguration configuration = new UserKeywordConfiguration();
        configuration.setUser(userOpted.get());
        configuration.setKeyword(keyword.trim());
        configuration.setKeywordConfigurationStatus(NotificationConfigurationStatus.ENABLED);
        userKeywordConfigurationRepository.save(configuration);
    }
}
