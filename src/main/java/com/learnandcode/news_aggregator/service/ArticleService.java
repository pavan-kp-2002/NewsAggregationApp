package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.ArticleDateRangeAndCategoryDTO;
import com.learnandcode.news_aggregator.model.Article;

import java.util.List;

public interface ArticleService {
     List<Article> getTodaysArticles();

     List<Article> getArticlesByDateRangeAndCategory(ArticleDateRangeAndCategoryDTO articleDateRangeAndCategoryDTO);

     List<Article> searchArticles(String searchTerm);
}
