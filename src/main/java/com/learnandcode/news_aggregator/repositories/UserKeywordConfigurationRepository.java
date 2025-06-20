package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.NotificationConfigurationStatus;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserKeywordConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordConfigurationRepository extends JpaRepository<UserKeywordConfiguration, Long> {
    List<UserKeywordConfiguration> findAllByUser(User user);
    boolean existsByUserAndKeyword(User user, String keyword);
    List<UserKeywordConfiguration> findBykeywordConfigurationStatus(NotificationConfigurationStatus status);
    List<UserKeywordConfiguration> findByUser(User user);
}
