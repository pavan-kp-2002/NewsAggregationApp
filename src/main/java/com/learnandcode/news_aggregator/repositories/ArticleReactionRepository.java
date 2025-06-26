package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ArticleReaction;
import com.learnandcode.news_aggregator.model.ReactionType;
import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleReactionRepository extends JpaRepository<ArticleReaction, Long> {
    Optional<ArticleReaction> findByUserAndArticle(User user, Article article);
    long countByArticleAndReactionType(Article article, ReactionType reactionType);
}
