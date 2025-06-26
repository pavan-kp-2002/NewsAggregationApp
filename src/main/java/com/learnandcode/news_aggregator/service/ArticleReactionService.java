package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.model.ReactionType;

public interface ArticleReactionService {
    void reactToArticle(Long articleId, ReactionType reactionType);
    Long countLikes(Long articleId);
    Long countDislikes(Long articleId);
}
