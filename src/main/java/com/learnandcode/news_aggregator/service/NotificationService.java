package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    public List<NotificationDTO> getNotificationsForUser();
}
