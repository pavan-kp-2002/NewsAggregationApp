package com.learnandcode.news_aggregator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "external_servers")
public class ExternalServer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long serverId;
    private String serverName;
    private String apiKey;
    private String endPoint;

    @Enumerated(EnumType.STRING)
    private ServerStatus status;

    private LocalDateTime lastAccessed;

    public ExternalServer() {
    }

    public ExternalServer(String serverName, String apiKey, ServerStatus status, LocalDateTime lastAccessed) {
        this.serverName = serverName;
        this.apiKey = apiKey;
        this.status = status;
        this.lastAccessed = lastAccessed;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Long getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
