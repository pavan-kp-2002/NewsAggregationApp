package com.learnandcode.news_aggregator.service;

import com.learnandcode.news_aggregator.dto.ApiKeyRequestDTO;
import com.learnandcode.news_aggregator.dto.CategoryDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerDetailsDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerStatusDTO;
import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.ExternalServer;

import java.util.List;

public interface AdminService {
    List<ExternalServerStatusDTO> getExternalServersStatus();
    List<ExternalServerDetailsDTO> getExternalServerDetails();
    ExternalServer getExternalServerById(Long id);
    ExternalServerDetailsDTO addExternalServer(ExternalServerDetailsDTO details);
    ExternalServerDetailsDTO updateExternalServer(Long id, ApiKeyRequestDTO apiKey);
    List<Category> getAllCategories();
    Category addCategory(CategoryDTO categoryNamerequest);



}
