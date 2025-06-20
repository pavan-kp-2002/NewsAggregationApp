package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_UserIdAndEmailSentFalse(Long userId);

    @Query("SELECT DISTINCT n.user.userId FROM Notification n WHERE n.emailSent = false")
    List<Long> findDistinctUserIdsWithUnsentEmails();
}
