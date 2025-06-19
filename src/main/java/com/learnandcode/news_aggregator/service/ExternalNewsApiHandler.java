package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ExternalServer;

import java.util.List;

public interface ExternalNewsApiHandler {
    boolean supports(String serverName);
    List<Article> fetchArticles(ExternalServer server) throws Exception;
}