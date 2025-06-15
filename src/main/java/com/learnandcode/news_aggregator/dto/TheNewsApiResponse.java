package com.learnandcode.news_aggregator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TheNewsApiResponse {
    private List<TheNewsApiArticleDTO> data;

    public List<TheNewsApiArticleDTO> getData() {
        return data;
    }

    public void setData(List<TheNewsApiArticleDTO> data) {
        this.data = data;
    }
}
