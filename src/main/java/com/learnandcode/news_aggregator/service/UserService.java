package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.model.User;

public interface UserService {
    public User getUserByUserName(String userName);
    public User createUser(User user);
}
