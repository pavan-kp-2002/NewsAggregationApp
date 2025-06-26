package com.learnandcode.news_aggregator.dto;

public class NotificationDTO {
    private Long articleId;
    private String title;
    private String linkToArticle;

    public NotificationDTO(Long articleId, String title, String linkToArticle) {
        this.title = title;
        this.articleId = articleId;
        this.linkToArticle = linkToArticle;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkToArticle() {
        return linkToArticle;
    }

    public void setLinkToArticle(String linkToArticle) {
        this.linkToArticle = linkToArticle;
    }
}
