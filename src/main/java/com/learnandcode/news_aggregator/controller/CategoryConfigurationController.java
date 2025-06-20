package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.model.Category;
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

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(adminService.getAllCategories());
    }

    @PostMapping()
    public ResponseEntity<String> editCategoryConfiguration(@RequestBody String categoryName) {
        boolean updated = categoryConfigurationService.editCategoryConfiguration(categoryName);

        if(updated){
            return ResponseEntity.ok("Category notification status updated successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found or no configuration exists.");
        }
    }
}


