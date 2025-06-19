package com.learnandcode.news_aggregator.exception;

public class ExternalServerNotFoundException extends RuntimeException{
    public ExternalServerNotFoundException(String message) {
        super(message);
    }
}
