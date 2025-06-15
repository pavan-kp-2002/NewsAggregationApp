package com.learnandcode.news_aggregator.service.util;

import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySetter {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryResolver categoryResolver;

    public Category setCategory(List<String> categories, String title, String description) {
        if(categories != null && !categories.isEmpty()){
            String categoryName = categories.getFirst();
            return categoryRepository.findByNameIgnoreCase(categoryName)
                    .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
        }else{
            return categoryResolver.resolveCategory(title, description);
        }
    }
}
