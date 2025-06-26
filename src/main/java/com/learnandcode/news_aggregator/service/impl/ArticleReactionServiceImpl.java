package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ArticleReaction;
import com.learnandcode.news_aggregator.model.ReactionType;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.repositories.ArticleReactionRepository;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.ArticleReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleReactionServiceImpl implements ArticleReactionService {
    @Autowired
    private ArticleReactionRepository articleReactionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void reactToArticle(Long articleId, ReactionType reactionType) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        if (!userOpted.isPresent()) {
            throw new UserNotFoundException("User with the given username does not exist: " + userName);
        }
        User user = userOpted.get();
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article with the given ID does not exist: " + articleId));
        ArticleReaction existingReaction = articleReactionRepository.findByUserAndArticle(user, article).orElse(null);;

        if(existingReaction == null) {
            ArticleReaction newReaction = new ArticleReaction();
            newReaction.setUser(user);
            newReaction.setArticle(article);
            newReaction.setReactionType(reactionType);
            articleReactionRepository.save(newReaction);
        } else {
            if(existingReaction.getReactionType() == reactionType) {
                articleReactionRepository.delete(existingReaction);
            } else {
                existingReaction.setReactionType(reactionType);
                articleReactionRepository.save(existingReaction);
            }
        }
    }

    @Override
    public Long countLikes(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article with the given ID does not exist: " + articleId));
        return articleReactionRepository.countByArticleAndReactionType(article, ReactionType.LIKE);
    }

    @Override
    public Long countDislikes(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article with the given ID does not exist: " + articleId));
        return articleReactionRepository.countByArticleAndReactionType(article, ReactionType.DISLIKE);

    }
}
