package com.learnandcode.news_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsAggregatorAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAggregatorAppApplication.class, args);
	}

}
