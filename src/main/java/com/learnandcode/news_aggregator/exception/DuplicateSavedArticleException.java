package com.learnandcode.news_aggregator.exception;

public class DuplicateSavedArticleException extends RuntimeException{
    public DuplicateSavedArticleException(String message) {
        super(message);
    }
}
