package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByUrl(String url);
    List<Article> findByPublishedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Article> findByPublishedAtBetweenAndCategoryId_IdOrderByPublishedAtDesc(LocalDateTime start, LocalDateTime end, Long categoryId);
    List<Article> findByTitleContainingIgnoreCaseOrderByPublishedAtDesc(String searchTerm);
}

