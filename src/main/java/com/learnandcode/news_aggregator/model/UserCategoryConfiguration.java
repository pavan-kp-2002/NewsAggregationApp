package com.learnandcode.news_aggregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_category_configurations")
public class UserCategoryConfiguration {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long configId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationConfigurationStatus notificationConfigurationStatus;

    public Long getConfigId() {
        return configId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationConfigurationStatus getNotificationConfigurationStatus() {
        return notificationConfigurationStatus;
    }

    public void setNotificationConfigurationStatus(NotificationConfigurationStatus notificationConfigurationStatus) {
        this.notificationConfigurationStatus = notificationConfigurationStatus;
    }
}
