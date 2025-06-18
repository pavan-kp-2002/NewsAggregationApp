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
            "Business", List.of(
                    "business", "finance", "economy", "market", "stocks", "trade", "investment", "corporate", "startup", "entrepreneur",
                    "merger", "acquisition", "revenue", "profit", "loss", "shareholder", "banking", "commerce", "industry", "tax"
            ),
            "Entertainment", List.of(
                    "entertainment", "arts", "culture", "movies", "music", "tv", "film", "celebrity", "show", "theater",
                    "festival", "concert", "drama", "comedy", "dance", "actor", "actress", "director", "award", "performance"
            ),
            "Health", List.of(
                    "health", "wellness", "medicine", "fitness", "nutrition", "disease", "mental", "hospital", "doctor", "covid",
                    "vaccine", "therapy", "surgery", "clinic", "diagnosis", "treatment", "exercise", "diet", "virus", "epidemic"
            ),
            "Science", List.of(
                    "science", "technology", "innovation", "research", "space", "biology", "physics", "chemistry", "discovery", "experiment",
                    "laboratory", "genetics", "astronomy", "scientist", "theory", "data", "analysis", "evolution", "ecology", "robotics"
            ),
            "Sports", List.of(
                    "sports", "athletics", "games", "football", "soccer", "cricket", "basketball", "tennis", "olympics", "tournament",
                    "match", "player", "coach", "score", "league", "championship", "medal", "race", "golf", "swimming"
            ),
            "Technology", List.of(
                    "technology", "gadgets", "tech", "software", "hardware", "ai", "artificial intelligence", "computer", "internet", "app",
                    "robotics", "programming", "coding", "cloud", "cybersecurity", "blockchain", "machine learning", "data science", "mobile", "web"
            )
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
