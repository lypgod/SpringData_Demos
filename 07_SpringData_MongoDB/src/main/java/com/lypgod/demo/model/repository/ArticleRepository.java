package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, Integer> {
    /**
     * 根据标题查询
     *
     * @param name String
     * @return List<Article>
     */
    List<Article> findByNameLike(String name);

    /**
     * 根据点击量查询
     *
     * @param hits Integer
     * @return List<Article>
     */
    List<Article> findByHitsGreaterThan(Integer hits);
}
