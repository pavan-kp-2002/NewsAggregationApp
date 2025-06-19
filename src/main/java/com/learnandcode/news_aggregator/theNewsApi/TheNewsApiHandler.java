package com.learnandcode.news_aggregator.theNewsApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnandcode.news_aggregator.model.Article;
import com.learnandcode.news_aggregator.model.ExternalServer;
import com.learnandcode.news_aggregator.repositories.ArticleRepository;
import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import com.learnandcode.news_aggregator.service.util.CategorySetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TheNewsApiHandler implements ExternalNewsApiHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategorySetter categorySetter;
    @Override
    public boolean supports(String serverName) {
        return "TheNewsAPI".equalsIgnoreCase(serverName);
    }

    @Override
    public List<Article> fetchArticles(ExternalServer server) throws Exception {
        String url = UriComponentsBuilder.fromUriString(server.getEndPoint())
                .queryParam("api_token", server.getApiKey())
                .queryParam("locale", "in, us")
                .queryParam("language", "en")
                .toUriString();
        String responseBody = new RestTemplate().getForObject(url, String.class);
        TheNewsApiResponse theNewsApiResponse = objectMapper.readValue(responseBody, TheNewsApiResponse.class);

        List<Article> articles = new ArrayList<>();
        if(theNewsApiResponse.getData() != null) {
            for(TheNewsApiArticleDTO dto : theNewsApiResponse.getData()){
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
