package com.learnandcode.news_aggregator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    @Column(length = 1024)
    private String title;

    @Column(length = 8192)
    private String description;

    @Column(length = 2048)
    private String url;

    @Column(length = 2048)
    private String urlToImage;

    private LocalDateTime publishedAt;
    private LocalDateTime fetchedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryId;

    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(LocalDateTime fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
