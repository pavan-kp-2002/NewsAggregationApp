package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.ApiKeyRequestDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerDetailsDTO;
import com.learnandcode.news_aggregator.dto.ExternalServerStatusDTO;
import com.learnandcode.news_aggregator.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servers")
public class ExternalServerController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<ExternalServerDetailsDTO> addExternalServer(@RequestBody ExternalServerDetailsDTO dto) {
        ExternalServerDetailsDTO created = adminService.addExternalServer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ExternalServerStatusDTO>> getAllServers() {
        List<ExternalServerStatusDTO> servers = adminService.getExternalServersStatus();
        return ResponseEntity.ok(servers);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ExternalServerDetailsDTO>> getServerDetails() {
        List<ExternalServerDetailsDTO> details = adminService.getExternalServerDetails();
        return ResponseEntity.ok(details);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExternalServerDetailsDTO> updateServer(@PathVariable Long id, @RequestBody ApiKeyRequestDTO apiKeyRequest) {
        ExternalServerDetailsDTO updated = adminService.updateExternalServer(id, apiKeyRequest);
        return ResponseEntity.ok(updated);
    }

}
