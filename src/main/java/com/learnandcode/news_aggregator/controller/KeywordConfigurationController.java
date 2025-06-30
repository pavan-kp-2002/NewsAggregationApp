package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.KeywordConfigurationDTO;
import com.learnandcode.news_aggregator.model.UserKeywordConfiguration;
import com.learnandcode.news_aggregator.service.impl.UserKeywordConfigurationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/keywords-config")
public class KeywordConfigurationController {

    @Autowired
    UserKeywordConfigurationServiceImpl userKeywordConfigurationService;
    @GetMapping
    public ResponseEntity<List<KeywordConfigurationDTO>> getUserKeywordConfigurations() {
        List<KeywordConfigurationDTO> configurations = userKeywordConfigurationService.getUserKeywordConfigurations();
        return ResponseEntity.ok(configurations);
    }
    @PostMapping
    public ResponseEntity<String> addUserKeywordConfiguration(@RequestBody String keyword) {
        userKeywordConfigurationService.addKeywordConfiguration(keyword);
        return ResponseEntity.status(201).body("Keyword configuration added successfully");
    }
    @PostMapping("/edit")
    public ResponseEntity<String> editUserKeywordConfiguration(@RequestBody String keyword) {
        userKeywordConfigurationService.editKeywordConfiguration(keyword);
        return ResponseEntity.status(201).body("Keyword configuration updated successfully");
    }
}
