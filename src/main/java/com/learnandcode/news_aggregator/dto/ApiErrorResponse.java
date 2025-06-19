package com.learnandcode.news_aggregator.dto;

public class ApiErrorResponse {
    private String message;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiErrorResponse(String message, int status){
        this.message = message;
        this.status = status;
    }
}
