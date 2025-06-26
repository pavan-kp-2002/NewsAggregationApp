package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.UserCategoryConfigurationDTO;
import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.NotificationConfigurationStatus;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserCategoryConfiguration;
import com.learnandcode.news_aggregator.repositories.CategoryRepository;
import com.learnandcode.news_aggregator.repositories.UserCategoryConfigurationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.UserCategoryConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCategoryConfigurationServiceImpl implements UserCategoryConfigurationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserCategoryConfigurationRepository userCategoryConfigurationRepository;

    @Override
    public List<UserCategoryConfigurationDTO> getUserCategoryConfigurations() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User with the given username does not exist"));
        List<UserCategoryConfiguration> userCategoryConfigurationList = userCategoryConfigurationRepository.findAllByUser(user);
        return userCategoryConfigurationList.stream().map(
                config -> new UserCategoryConfigurationDTO(
                        config.getCategory().getName(),
                        config.getNotificationConfigurationStatus().toString()
                )
        ).toList();
    }

    @Override
    public boolean editCategoryConfiguration(String categoryName) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User with the given username does not exist"));
        Optional<Category> category = categoryRepository.findByNameIgnoreCase(categoryName);

        if(category.isPresent()){
            Optional<UserCategoryConfiguration> configuration = userCategoryConfigurationRepository.findByUserAndCategory(user, category.get());
            if(configuration.isPresent()){
                UserCategoryConfiguration config = configuration.get();
                NotificationConfigurationStatus currentStatus = config.getNotificationConfigurationStatus();

                config.setNotificationConfigurationStatus(
                        currentStatus == NotificationConfigurationStatus.ENABLED
                                ? NotificationConfigurationStatus.DISABLED
                                : NotificationConfigurationStatus.ENABLED
                );
                userCategoryConfigurationRepository.save(config);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCategoryConfiguration(String categoryName) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User with the given username does not exist"));
        Optional<Category> category = categoryRepository.findByNameIgnoreCase(categoryName);
        if(category.isPresent()){
            Optional<UserCategoryConfiguration> existingConfig = userCategoryConfigurationRepository.findByUserAndCategory(user, category.get());
            if(existingConfig.isPresent()){
                return false;
            }
            UserCategoryConfiguration newConfig = new UserCategoryConfiguration();
            newConfig.setUser(user);
            newConfig.setCategory(category.get());
            newConfig.setNotificationConfigurationStatus(NotificationConfigurationStatus.ENABLED);
            userCategoryConfigurationRepository.save(newConfig);
            return true;
        }
        return false;
    }


}
