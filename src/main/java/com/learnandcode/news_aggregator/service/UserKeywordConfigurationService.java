package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.KeywordConfigurationDTO;
import com.learnandcode.news_aggregator.model.UserKeywordConfiguration;

import java.util.List;

public interface UserKeywordConfigurationService {
    List<KeywordConfigurationDTO> getUserKeywordConfigurations();
    void addKeywordConfiguration(String keyword);
    void editKeywordConfiguration(String keyword);

}
