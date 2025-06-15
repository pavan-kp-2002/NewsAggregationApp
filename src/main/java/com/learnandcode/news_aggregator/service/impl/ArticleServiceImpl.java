package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.ArticleDaterangeDTO;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> getTodaysArticles() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
        return articleRepository.findByPublishedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Article> getArticlesByDateRange(ArticleDaterangeDTO articleDaterangeDTO) {
        LocalDateTime start = articleDaterangeDTO.getStartDate().atStartOfDay();
        LocalDateTime end = articleDaterangeDTO.getEndDate().atTime(23, 59, 59);
        return articleRepository.findByPublishedAtBetweenAndCategoryId_IdOrderByPublishedAtDesc(start, end, articleDaterangeDTO.getCategoryId());
    }

    @Override
    public List<Article> searchArticles(String searchTerm) {
        return articleRepository.findByTitleContainingIgnoreCaseOrderByPublishedAtDesc(searchTerm);
    }


}
