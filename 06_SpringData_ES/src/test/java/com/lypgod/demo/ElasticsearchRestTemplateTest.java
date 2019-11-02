package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RestClientConfig.class)
public class ElasticsearchRestTemplateTest {
    @Resource
    private ElasticsearchOperations elasticsearchOperations;
    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void queryForObjectTest() {
        Article article = elasticsearchOperations.queryForObject(GetQuery.getById("1"), Article.class);
        System.out.println(article);
    }

    @Test
    public void saveTest() {
        Article article = new Article().setId(21).setTitle("new title").setContext("new Content").setHits(111);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId("20")
                .withObject(article)
                .build();
        System.out.println(elasticsearchOperations.index(indexQuery));
    }

    // 修改
    @Test
    public void testUpdate() {
        // 判断数据库中是否有你指定的id的文档,如果没有,就进行保存,如果有,就进行更新
        Article article = new Article();
        article.setId(1);
        article.setTitle("xx-黑马程序员1");
        article.setContext("xx-黑马程序员很棒1");

        articleRepository.save(article);
    }
}
