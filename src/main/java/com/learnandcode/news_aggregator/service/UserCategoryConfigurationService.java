package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.UserCategoryConfigurationDTO;
import com.learnandcode.news_aggregator.model.UserCategoryConfiguration;

import java.util.List;

public interface UserCategoryConfigurationService {
    boolean editCategoryConfiguration(String categoryName);
    boolean addCategoryConfiguration(String categoryName);
    List<UserCategoryConfigurationDTO> getUserCategoryConfigurations();
}
