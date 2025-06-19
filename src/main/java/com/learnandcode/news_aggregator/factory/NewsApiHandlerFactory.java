package com.learnandcode.news_aggregator.factory;

import com.learnandcode.news_aggregator.service.ExternalNewsApiHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsApiHandlerFactory {
    private final List<ExternalNewsApiHandler> handlers;

    @Autowired
    public NewsApiHandlerFactory(List<ExternalNewsApiHandler> handlers) {
        this.handlers = handlers;
    }

    public ExternalNewsApiHandler getHandler(String serverName) {
        return handlers.stream()
                .filter(h -> h.supports(serverName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No handler found for server: " + serverName));
    }
}
