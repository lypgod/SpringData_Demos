package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTypeRepository extends JpaRepository<ArticleType, Integer> {
}
