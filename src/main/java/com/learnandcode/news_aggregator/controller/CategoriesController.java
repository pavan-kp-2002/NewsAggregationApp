package com.learnandcode.news_aggregator.controller;

import com.learnandcode.news_aggregator.dto.CategoryDTO;
import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = adminService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryNameRequest, Principal principal) {
        System.out.println(principal);
        Category category = adminService.addCategory(categoryNameRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
