package com.learnandcode.news_aggregator.newsApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ExternalServer;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import com.learnandcode.news_aggregator.service.util.CategoryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public  class NewsApiHandler implements ExternalNewsApiHandler {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryResolver categoryResolver;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(String serverName) {
        return "NewsAPI".equalsIgnoreCase(serverName);
    }

    @Override
    public List<Article> fetchArticles(ExternalServer server) throws Exception {
        String url = UriComponentsBuilder.fromUriString(server.getEndPoint())
                .queryParam("apiKey",server.getApiKey())
                .toUriString();
        String responseBody = new RestTemplate().getForObject(url, String.class);
        NewsApiResponse response = objectMapper.readValue(responseBody, NewsApiResponse.class);

        List<Article> articles = new ArrayList<>();
        if(response.getArticles() != null) {
            for(NewsApiArticleDTO dto : response.getArticles()){
                if(dto.getUrl() != null && !articleRepository.existsByUrl(dto.getUrl())){
                    Article article = new Article();
                    article.setTitle(dto.getTitle());
                    article.setDescription(dto.getDescription());
                    article.setUrl(dto.getUrl());
                    article.setUrlToImage(dto.getUrlToImage());
                    article.setPublishedAt(dto.getPublishedAt());
                    article.setFetchedAt(LocalDateTime.now());
                    article.setCategoryId(categoryResolver.resolveCategory(dto.getTitle(),dto.getDescription()));
                    articles.add(article);
                }
            }
        }
        return articles;
    }
}
