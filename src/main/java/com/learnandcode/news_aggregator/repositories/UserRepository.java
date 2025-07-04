package com.learnandcode.news_aggregator.repositories;

import com.learnandcode.news_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
     boolean existsByEmail(String email);
     boolean existsByUsername(String username);
     Optional<User> findByEmail(String email);
     Optional<User> findByUsername(String username);
}
