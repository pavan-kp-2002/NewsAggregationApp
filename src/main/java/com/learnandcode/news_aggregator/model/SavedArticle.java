package com.learnandcode.news_aggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "saved_articles")
public class SavedArticle {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long savedArticleId;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setArticle(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSavedArticleId() {
        return savedArticleId;
    }
}
