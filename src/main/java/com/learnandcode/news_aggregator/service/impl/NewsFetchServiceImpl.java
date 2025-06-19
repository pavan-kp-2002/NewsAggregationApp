package com.learnandcode.news_aggregator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregator.factory.NewsApiHandlerFactory;
import com.learnandcode.news_aggregator.newsApi.NewsApiArticleDTO;
import com.learnandcode.news_aggregator.newsApi.NewsApiResponse;
import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import com.learnandcode.news_aggregator.theNewsApi.TheNewsApiArticleDTO;
import com.learnandcode.news_aggregator.theNewsApi.TheNewsApiResponse;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ExternalServer;
import com.learnandcode.news_aggregator.model.ServerStatus;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.repositories.ExternalServerRepository;
import com.learnandcode.news_aggregator.service.NewsFetchService;
import com.learnandcode.news_aggregator.service.util.CategoryResolver;
import com.learnandcode.news_aggregator.service.util.CategorySetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsFetchServiceImpl implements NewsFetchService {
    @Autowired
    private ExternalServerRepository externalServerRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NewsApiHandlerFactory handlerFactory;

    //private final long fetchInterval = 4 * 60 * 60 * 1000;
    private final long testInterval = 1 * 60 * 1000; // for testing
    @Override
    @Scheduled(fixedRate = testInterval)
    public void fetchArticlesFromAllExternalApis() {
       List<ExternalServer> externalServers = externalServerRepository.findAll();

       for (ExternalServer server : externalServers) {
            try {
                ExternalNewsApiHandler handler = handlerFactory.getHandler(server.getServerName());
                List<Article> articles = handler.fetchArticles(server);
                articleRepository.saveAll(articles);

                server.setStatus(ServerStatus.ACTIVE);
            }catch (Exception e){
                server.setStatus(ServerStatus.INACTIVE);
                System.out.println(e.getMessage());
            }
            server.setLastAccessed(LocalDateTime.now());
            externalServerRepository.save(server);
        }
    }
}
