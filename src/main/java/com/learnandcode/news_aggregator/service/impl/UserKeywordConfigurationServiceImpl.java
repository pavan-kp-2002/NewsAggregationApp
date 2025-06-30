package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.KeywordConfigurationDTO;
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
    public List<KeywordConfigurationDTO> getUserKeywordConfigurations() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);

        if (userOpted.isPresent()) {
            List<UserKeywordConfiguration> userKeywordConfigurationList =  userKeywordConfigurationRepository.findAllByUser(userOpted.get());
            if (userKeywordConfigurationList.isEmpty()) {
                throw new UserNotFoundException("No keyword configurations found for the user ");
            }else {
                return userKeywordConfigurationList.stream()
                        .map(config -> new KeywordConfigurationDTO(
                                config.getKeyword(),
                                config.getKeywordConfigurationStatus().toString()))
                        .toList();
            }
        } else {
            throw new UserNotFoundException("User with the given username does not exist " + userName);
        }
    }


    @Override
    public void editKeywordConfiguration(String keyword) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if(!userOpted.isPresent()){
            throw new UserNotFoundException("User with the given username does not exist "+ userName);
        }
//        boolean exists = userKeywordConfigurationRepository.existsByUserAndKeyword(userOpted.get(), keyword);
//        if(!exists){
//            throw new IllegalArgumentException("Keyword not set for the user");
//        }
        Optional<UserKeywordConfiguration> configurationOpt = userKeywordConfigurationRepository.findByUserAndKeyword(userOpted.get(), keyword);
        if(configurationOpt.isPresent()){
            UserKeywordConfiguration configuration = configurationOpt.get();
            NotificationConfigurationStatus currentStatus = configuration.getKeywordConfigurationStatus();

            configuration.setKeywordConfigurationStatus(
                    currentStatus == NotificationConfigurationStatus.ENABLED
                            ? NotificationConfigurationStatus.DISABLED
                            : NotificationConfigurationStatus.ENABLED
            );
            userKeywordConfigurationRepository.save(configuration);
        } else {
            throw new IllegalArgumentException("Keyword configuration not found for the user");
        }
    }

    public void addKeywordConfiguration(String keyword) {
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
