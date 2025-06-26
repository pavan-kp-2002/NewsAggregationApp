package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.model.ReactionType;
import com.learnandcode.news_aggregator.service.ArticleReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article-reactions")
public class ArticleReactionController {
    @Autowired
    private ArticleReactionService articleReactionService;
    @PostMapping("/{articleId}/react")
    public ResponseEntity<String> reactToArticle(@PathVariable Long articleId, @RequestParam ReactionType reactionType){
        articleReactionService.reactToArticle(articleId, reactionType);
        return ResponseEntity.ok("Reaction recorded successfully");
    }

    @GetMapping("/{articleId}/likes")
    public ResponseEntity<Long> getLikes(@PathVariable Long articleId) {
        Long likesCount = articleReactionService.countLikes(articleId);
        return ResponseEntity.ok(likesCount);
    }

    @GetMapping("/{articleId}/dislikes")
    public ResponseEntity<Long> getDislikes(@PathVariable Long articleId) {
        Long dislikesCount = articleReactionService.countDislikes(articleId);
        return ResponseEntity.ok(dislikesCount);
    }
}
