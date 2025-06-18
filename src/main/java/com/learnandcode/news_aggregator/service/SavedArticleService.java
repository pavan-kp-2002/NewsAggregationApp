package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.model.SavedArticle;

import java.util.List;

public interface SavedArticleService {
    void saveArticle(Long articleId);
    void deleteArticle(Long articleId);
    List<SavedArticle> getSavedArticlesByUserId();
}
