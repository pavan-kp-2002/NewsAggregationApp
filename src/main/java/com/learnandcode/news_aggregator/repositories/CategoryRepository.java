package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Optional<Category> findByNameIgnoreCase(String name);
    Optional<Category> findByNameStartingWithIgnoreCase(String name);
}
