package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Notification;
import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndEmailSentFalse(User user);
}
