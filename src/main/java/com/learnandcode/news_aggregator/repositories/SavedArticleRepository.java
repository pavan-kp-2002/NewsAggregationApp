package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.SavedArticle;
import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends JpaRepository<SavedArticle, Long> {
    boolean existsByUserAndArticle(User user, Article article);
    Optional<SavedArticle> findByUserAndArticle(User user, Article article);
    List<SavedArticle> findAllByUser(User user);
}
