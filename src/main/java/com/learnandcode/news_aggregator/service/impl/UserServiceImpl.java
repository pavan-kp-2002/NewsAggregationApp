package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.repositories.CategoryRepository;
import com.learnandcode.news_aggregator.repositories.UserCategoryConfigurationRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserCategoryConfigurationRepository userNotificationConfigurationRepository;
    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User with the given username does not exist"));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }
        else if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("A user with this username already exists.");
        }

        return userRepository.save(user);
    }

}
