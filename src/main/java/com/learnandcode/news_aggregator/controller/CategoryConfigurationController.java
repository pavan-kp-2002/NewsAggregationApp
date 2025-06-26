package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.UserCategoryConfigurationDTO;
import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.model.UserCategoryConfiguration;
import com.learnandcode.news_aggregator.service.AdminService;
import com.learnandcode.news_aggregator.service.UserCategoryConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category-configurations")
public class CategoryConfigurationController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserCategoryConfigurationService categoryConfigurationService;

//    @GetMapping
//    public ResponseEntity<List<Category>> getAllCategories() {
//        return ResponseEntity.ok(adminService.getAllCategories());
//    }
    @GetMapping
    public ResponseEntity<List<UserCategoryConfigurationDTO>> getUserCategoryConfigurations() {
        List<UserCategoryConfigurationDTO> configurations = categoryConfigurationService.getUserCategoryConfigurations();
        return ResponseEntity.ok(configurations);
    }
    @PostMapping("/edit")
    public ResponseEntity<String> editCategoryConfiguration(@RequestBody String categoryName) {
        boolean updated = categoryConfigurationService.editCategoryConfiguration(categoryName);

        if(updated){
            return ResponseEntity.ok("Category notification status updated successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found or no configuration exists.");
        }
    }

    @PostMapping
    public ResponseEntity<String> addNewCategoryConfiguration(@RequestBody String categoryName) {
        boolean added = categoryConfigurationService.addCategoryConfiguration(categoryName);

        if(added){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("New Category configuration added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Category configuration already exists");
            }
    }
}


