package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.exception.ArticleNotFoundException;
import com.learnandcode.news_aggregator.exception.DuplicateSavedArticleException;
import com.learnandcode.news_aggregator.exception.UserNotFoundException;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.SavedArticle;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.repositories.SavedArticleRepository;
import com.learnandcode.news_aggregator.repositories.UserRepository;
import com.learnandcode.news_aggregator.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleServiceImpl implements SavedArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedArticleRepository savedArticleRepository;


    @Override
    public void saveArticle(Long articleId) {
        Optional<Article> articleOpted = articleRepository.findById(articleId);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);

        if(articleOpted.isPresent() && userOpted.isPresent()){
            if(savedArticleRepository.existsByUserAndArticle(userOpted.get(), articleOpted.get())){
                throw new DuplicateSavedArticleException("Article already saved by this user.");
            }
            else{
                SavedArticle savedArticle = new SavedArticle();
                savedArticle.setArticle(articleOpted.get());
                savedArticle.setUser(userOpted.get());
                savedArticleRepository.save(savedArticle);
            }
        }
        else {
            throw new ArticleNotFoundException("Article with the given ID does not exist");
        }
    }

    @Override
    public void deleteArticle(Long articleId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);
        Optional<Article> articleOpted = articleRepository.findById(articleId);
        if(articleOpted.isEmpty()){
            throw new ArticleNotFoundException("Article with the given ID does not exist");
        }
        if(userOpted.isEmpty()){
            throw new UserNotFoundException("User with the given username does not exist");
        }

        Optional<SavedArticle> savedArticle = savedArticleRepository.findByUserAndArticle(userOpted.get(), articleOpted.get());

        if (savedArticle.isPresent()) {
            savedArticleRepository.delete(savedArticle.get());
        } else {
            throw new IllegalArgumentException("Saved article not found for this user and article");
        }
    }

    @Override
    public List<SavedArticle> getSavedArticlesByUserId() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpted = userRepository.findByUsername(userName);

        if (userOpted.isPresent()) {
            return savedArticleRepository.findAllByUser(userOpted.get());
        } else {
            throw new UserNotFoundException("User with the given username does not exist");
        }
    }


}
