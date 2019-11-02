package com.lypgod.demo.springdataproject;

import com.lypgod.demo.springdataproject.model.entity.Article;
import com.lypgod.demo.springdataproject.model.entity.ArticleData;
import com.lypgod.demo.springdataproject.model.entity.Comment;
import com.lypgod.demo.springdataproject.model.entity.EsArticle;
import com.lypgod.demo.springdataproject.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Resource
    private ArticleService articleService;

    @Test
    public void saveArticleDbTest() {
        Article article = new Article().setTitle("title").setAuthor("Author");
        System.out.println(articleService.saveArticleDb(article));
    }

    @Test
    public void saveArticleRedisTest() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article()
                .setId(1)
                .setTitle("title")
                .setAuthor("Author")
                .setArticleData(new ArticleData().setId(11).setContent("article data"))
                .setCreateTime(LocalDateTime.now()));
        articles.add(new Article()
                .setId(2)
                .setTitle("title2")
                .setAuthor("Author2")
                .setArticleData(new ArticleData().setId(22).setContent("article data2"))
                .setCreateTime(LocalDateTime.now()));
        articleService.saveArticlesRedis(articles);
    }

    @Test
    public void getArticlesRedisTest() {
        articleService.getArticlesRedis().forEach(System.out::println);
    }

    @Test
    public void saveArticleEsOperationsTest() {
        EsArticle esArticle = new EsArticle()
                .setId(2)
                .setTitle("title")
                .setAuthor("Author")
                .setContent("content")
                .setCreateTime(LocalDateTime.now());
        System.out.println(articleService.saveArticleEsOperations(esArticle));
    }

    @Test
    public void saveArticleEsRepositoryTest() {
        EsArticle esArticle = new EsArticle()
                .setId(3)
                .setTitle("title3")
                .setAuthor("Author3")
                .setContent("content3")
                .setCreateTime(LocalDateTime.now());
        System.out.println(articleService.saveArticleEsRepository(esArticle));
    }

    @Test
    public void getArticleElasticsearchTest() {
        System.out.println(articleService.findEsArticle(String.valueOf(2)));
    }

    @Test
    public void saveCommentMongoTest() {
        Comment comment = new Comment().setId("11").setArticleId(1).setComment("comment").setNickname("nickname");
        articleService.saveCommentMongo(comment);
    }
}
