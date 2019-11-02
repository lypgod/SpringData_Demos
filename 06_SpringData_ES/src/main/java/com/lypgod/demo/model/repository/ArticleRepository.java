package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义的接口需要继承ElasticsearchRepository<实体类型, 主键类型>  基本的crud 分页
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {
    /**
     * 根据标题查询
     *
     * @param title String
     * @return List<Article>
     */
    List<Article> findByTitle(String title);

    /**
     * 根据标题或内容查询
     *
     * @param title   String
     * @param context String
     * @return List<Article>
     */
    List<Article> findByTitleOrContext(String title, String context);

    /**
     * 根据标题或内容查询(含分页)
     *
     * @param title    String
     * @param context  String
     * @param pageable Pageable
     * @return List<Article>
     */
    List<Article> findByTitleOrContext(String title, String context, Pageable pageable);
}
