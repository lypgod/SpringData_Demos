package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Integer> {
}
