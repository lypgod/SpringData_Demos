package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
