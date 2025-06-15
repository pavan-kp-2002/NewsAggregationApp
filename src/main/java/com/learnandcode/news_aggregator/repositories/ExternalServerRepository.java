package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.ExternalServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalServerRepository extends JpaRepository<ExternalServer, Long> {
}
