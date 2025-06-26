package com.learnandcode.news_aggregator.dto;

public class KeywordConfigurationDTO {
    private String keyword;
    private String status;

    public KeywordConfigurationDTO(String keyword, String status) {
        this.keyword = keyword;
        this.status = status;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
