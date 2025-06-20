package com.learnandcode.news_aggregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_keyword_configuration")
public class UserKeywordConfiguration {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String keyword;

    @Enumerated(EnumType.STRING)
    private NotificationConfigurationStatus keywordConfigurationStatus;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationConfigurationStatus getKeywordConfigurationStatus() {
        return keywordConfigurationStatus;
    }

    public void setKeywordConfigurationStatus(NotificationConfigurationStatus keywordConfigurationStatus) {
        this.keywordConfigurationStatus = keywordConfigurationStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
