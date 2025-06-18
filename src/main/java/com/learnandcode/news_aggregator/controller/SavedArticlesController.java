package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.model.SavedArticle;
import com.learnandcode.news_aggregator.model.User;
import com.learnandcode.news_aggregator.service.SavedArticleService;
import com.learnandcode.news_aggregator.service.UserService;
import com.learnandcode.news_aggregator.service.impl.SavedArticleServiceImpl;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/saved-articles")
public class SavedArticlesController {
    @Autowired
    SavedArticleService savedArticleService;
    @Autowired
    UserService userService;

    @PostMapping("/{id}")
    public ResponseEntity<String> saveArticle(@PathVariable Long id){
        savedArticleService.saveArticle(id);
        return ResponseEntity.ok("Article saved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSavedArticle(@PathVariable Long id){
        savedArticleService.deleteArticle(id);
        return ResponseEntity.ok("Article deleted successfully");
    }

    @GetMapping()
    public ResponseEntity<List<SavedArticle>> getSavedArticles(){
        return ResponseEntity.ok(savedArticleService.getSavedArticlesByUserId());
    }


}
