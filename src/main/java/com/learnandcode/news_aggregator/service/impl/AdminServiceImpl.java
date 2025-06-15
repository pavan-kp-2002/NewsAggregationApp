package com.learnandcode.news_aggregator.service.impl;

import com.learnandcode.news_aggregator.dto.ApiKeyRequestDTO;
import com.learnandcode.news_aggregator.dto.CategoryDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerDetailsDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerStatusDTO;
import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.ExternalServer;
import com.learnandcode.news_aggregator.model.ServerStatus;
import com.learnandcode.news_aggregator.repositories.CategoryRepository;
import com.learnandcode.news_aggregator.repositories.ExternalServerRepository;
import com.learnandcode.news_aggregator.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ExternalServerRepository externalServerRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public ExternalServerDetailsDTO addExternalServer(ExternalServerDetailsDTO dto) {
        ExternalServer server = new ExternalServer();
        server.setServerName(dto.getServerName());
        server.setApiKey(dto.getApiKey());
        server.setEndPoint(dto.getEndPoint());
        server.setStatus(ServerStatus.ACTIVE);
        server.setLastAccessed(LocalDateTime.now());
        ExternalServer saved = externalServerRepo.save(server);
        return mapToDetailsDTO(saved);
    }

    @Override
    public List<ExternalServerStatusDTO> getExternalServersStatus() {
        return externalServerRepo.findAll().stream().map(this::mapToStatusDTO).collect((Collectors.toList()));
    }

    @Override
    public List<ExternalServerDetailsDTO> getExternalServerDetails() {
        return externalServerRepo.findAll().stream().map(this::mapToDetailsDTO).collect(Collectors.toList());
    }

    @Override
    public ExternalServer getExternalServerById(Long id) {
        return externalServerRepo.findById(id).orElseThrow(() -> new RuntimeException("External server not found with id: " + id));
    }

    @Override
    public ExternalServerDetailsDTO updateExternalServer(Long id, ApiKeyRequestDTO apiKeyRequest) {
        ExternalServer externalServer = getExternalServerById(id);
        externalServer.setApiKey(apiKeyRequest.getApiKey());
        ExternalServer updated = externalServerRepo.save(externalServer);
        return mapToDetailsDTO(updated);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(CategoryDTO categoryNamerequest) {
        if(categoryRepo.existsByName(categoryNamerequest.getName())){
            throw new RuntimeException("Category already exists");
        }
        Category category = new Category();
        category.setName(categoryNamerequest.getName());
        return categoryRepo.save(category);
    }

    private ExternalServerStatusDTO mapToStatusDTO(ExternalServer server) {
        ExternalServerStatusDTO dto = new ExternalServerStatusDTO();
        dto.setServerName(server.getServerName());
        dto.setStatus(server.getStatus());
        dto.setLastAccessed(server.getLastAccessed());
        return dto;
    }

    private ExternalServerDetailsDTO mapToDetailsDTO(ExternalServer server) {
        ExternalServerDetailsDTO dto = new ExternalServerDetailsDTO();
        dto.setServerName(server.getServerName());
        dto.setApiKey(server.getApiKey());
        return dto;
    }
}
