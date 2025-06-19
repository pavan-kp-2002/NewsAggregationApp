package com.learnandcode.news_aggregator.exception;

public class SavedArticleNotFoundException extends RuntimeException{
    public SavedArticleNotFoundException(String message) {
        super(message);
    }
}
