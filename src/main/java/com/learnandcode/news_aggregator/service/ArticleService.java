package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.ArticleDaterangeDTO;
import com.learnandcode.news_aggregator.model.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> getTodaysArticles();

    public List<Article> getArticlesByDateRange(ArticleDaterangeDTO articleDaterangeDTO);

    public List<Article> searchArticles(String searchTerm);
}
