package com.learnandcode.news_aggregator.dto;

public class UserCategoryConfigurationDTO {
    private String categoryName;
    private String status;

    public UserCategoryConfigurationDTO(String categoryName, String status) {
        this.categoryName = categoryName;
        this.status = status;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
