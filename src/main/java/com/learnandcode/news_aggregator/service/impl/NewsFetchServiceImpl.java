package com.learnandcode.news_aggregator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregator.dto.NewsApiArticleDTO;
import com.learnandcode.news_aggregator.dto.NewsApiResponse;
import com.learnandcode.news_aggregator.dto.TheNewsApiArticleDTO;
import com.learnandcode.news_aggregator.dto.TheNewsApiResponse;
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
    private CategoryResolver categoryResolver;
    @Autowired
    private CategorySetter categorySetter;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
    }
    private final long fetchInterval = 4 * 60 * 60 * 1000;
    private final long testInterval = 5 * 60 * 1000; // 5 minutes for testing
    @Override
    @Scheduled(fixedRate = testInterval)
    public void fetchFromAllExternalApis() {
        List<ExternalServer> externalServers = externalServerRepository.findAll();
        for(ExternalServer externalServer : externalServers){
            try {
                String url = buildUrlWithApiKey(externalServer);
                String responseBody = restTemplate.getForObject(url, String.class);

                List<Article> articles = new ArrayList<>();
                switch (externalServer.getServerName()){
                    case "NewsAPI":
                        NewsApiResponse newsApiResponse = objectMapper.readValue(responseBody, NewsApiResponse.class);
                        articles = mapNewsApiArticles(newsApiResponse);
                        articleRepository.saveAll(articles);
                        break;
                    case "TheNewsAPI":
                        TheNewsApiResponse theNewsApiResponse = objectMapper.readValue(responseBody, TheNewsApiResponse.class);
                        articles = mapTheNewsApiArticles(theNewsApiResponse);
                        articleRepository.saveAll(articles);
                        break;
                    default:
                        break;

                }
                externalServer.setStatus(ServerStatus.ACTIVE);
                externalServer.setLastAccessed(LocalDateTime.now());
            }catch (Exception e){
                externalServer.setLastAccessed(LocalDateTime.now());
                externalServer.setStatus(ServerStatus.INACTIVE);
                System.out.println(e.getMessage());
            }
            externalServerRepository.save(externalServer);
        }

    }
    private String buildUrlWithApiKey(ExternalServer server){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(server.getEndPoint());
        switch (server.getServerName()){
            case "NewsAPI":
                builder.queryParam("apiKey", server.getApiKey());
                break;
            case "TheNewsAPI":
                builder.queryParam("api_token", server.getApiKey());
                builder.queryParam("locale", "in,us");
                builder.queryParam("language", "en");
                break;
            default:
                break;
        }
        return builder.toUriString();
    }

    private List<Article> mapNewsApiArticles(NewsApiResponse response){
        List<Article> articles = new ArrayList<>();
        if(response.getArticles() != null) {
            for (NewsApiArticleDTO dto : response.getArticles()){
                if(dto.getUrl() != null && !articleRepository.existsByUrl(dto.getUrl())){
                    Article article = new Article();
                    article.setTitle(dto.getTitle());
                    article.setDescription(dto.getDescription());
                    article.setUrl(dto.getUrl());
                    article.setUrlToImage(dto.getUrlToImage());
                    article.setPublishedAt(dto.getPublishedAt());
                    article.setFetchedAt(LocalDateTime.now());
                    article.setCategoryId(categoryResolver.resolveCategory(dto.getTitle(), dto.getDescription()));
                    articles.add(article);
                }
            }
        }
        return articles;
    }
    private List<Article> mapTheNewsApiArticles(TheNewsApiResponse response) {
        List<Article> articles = new ArrayList<>();
        if (response.getData() != null) {
            for (TheNewsApiArticleDTO dto : response.getData()) {
                if(dto.getUrl() != null && !articleRepository.existsByUrl(dto.getUrl())){
                    Article article = new Article();
                    article.setTitle(dto.getTitle());
                    article.setDescription(dto.getDescription());
                    article.setUrl(dto.getUrl());
                    article.setUrlToImage(dto.getImage_url());
                    article.setPublishedAt(dto.getPublished_at());
                    article.setFetchedAt(LocalDateTime.now());
                    article.setCategoryId(categorySetter.setCategory(dto.getCategories(), dto.getTitle(), dto.getDescription()));
                }
            }
        }
        return articles;
    }
}
