package com.learnandcode.news_aggregator.service.util;

import com.learnandcode.news_aggregator.model.Category;
import com.learnandcode.news_aggregator.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CategoryResolver {
    private static final Map<String, List<String>> CATEGORY_MAP = Map.of(
        "Business", List.of("business", "finance", "economy"),
        "Entertainment", List.of("entertainment", "arts", "culture"),
        "Health", List.of("health", "wellness", "medicine"),
        "Science", List.of("science", "technology", "innovation"),
        "Sports", List.of("sports", "athletics", "games"),
        "Technology", List.of("technology", "gadgets", "tech")
    );

    @Autowired
    private CategoryRepository categoryRepository;

    public Category resolveCategory(String title, String description){
        String text = (title + " " + description).toLowerCase();
        for(var entry: CATEGORY_MAP.entrySet()){
            for(String keyword : entry.getValue()){
                if(text.contains(keyword.toLowerCase())){
                    return getOrCreateCategory(entry.getKey());
                }
            }
        }
        return getOrCreateCategory("All");
    }

    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
    }
}
