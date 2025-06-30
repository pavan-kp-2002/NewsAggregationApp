package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.NotificationConfigurationStatus;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.model.UserCategoryConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryConfigurationRepository extends JpaRepository<UserCategoryConfiguration, Long> {
    List<UserCategoryConfiguration> findByCategoryAndNotificationConfigurationStatus(
            Category category,
            NotificationConfigurationStatus status
    );
    Optional<UserCategoryConfiguration> findByUserAndCategory(User user, Category category);

    List<UserCategoryConfiguration> findAllByUser(User user);
}
