package com.lypgod.demo.springdataproject.model.repository;

import com.lypgod.demo.springdataproject.model.entity.EsArticle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsArticleRepository extends ElasticsearchRepository<EsArticle, Integer> {
    /**
     * 根据title或者content进行查询
     *
     * @param title    String
     * @param content  String
     * @param pageable Pageable
     * @return List<EsArticle>
     */
    List<EsArticle> findByTitleOrContent(String title, String content, Pageable pageable);
}