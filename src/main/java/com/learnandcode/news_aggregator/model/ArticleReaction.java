package com.learnandcode.news_aggregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "article_reactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article_id", "user_id"})
})
public class ArticleReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleReactionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getArticleReactionId() {
        return articleReactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
