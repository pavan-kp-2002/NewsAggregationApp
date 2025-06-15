package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.ArticleDaterangeDTO;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticlesController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/today")
    public ResponseEntity<List<Article>> getTodaysArticles() {
        List<Article> articles = articleService.getTodaysArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Article>> getArticlesByDateRange(@RequestBody ArticleDaterangeDTO dateRangeDTO){
        List<Article> articles = articleService.getArticlesByDateRange(dateRangeDTO);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<Article>> searchArticles(@PathVariable String searchTerm){
        List<Article> articles = articleService.searchArticles(searchTerm);
        return ResponseEntity.ok(articles);
    }
}
