package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.NotificationDTO;
import com.learnandcode.news_aggregator.model.Notification;
import com.learnandcode.news_aggregator.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser() {
        List<NotificationDTO> notificationDTOList = notificationService.getNotificationsForUser();
        return ResponseEntity.ok(notificationDTOList);
    }
}
