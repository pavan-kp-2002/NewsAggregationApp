package com.learnandcode.news_aggregator.dto;

import com.learnandcode.news_aggregator.model.ServerStatus;

import java.time.LocalDateTime;

public class ExternalServerStatusDTO {
    private String serverName;
    private ServerStatus status;
    private LocalDateTime lastAccessed;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

}
