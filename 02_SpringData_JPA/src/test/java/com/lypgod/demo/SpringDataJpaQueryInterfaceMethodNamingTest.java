package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaQueryInterfaceMethodNamingTest {
    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void testFindByTitle() {
        List<Article> articles = articleRepository.findByTitle("标题1");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByTitleLike() {
        List<Article> articles = articleRepository.findByTitleLike("%标题%");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByTitleAndAuthor() {
        List<Article> articles = articleRepository.findByTitleAndAuthor("标题1", "作者1");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByIdLessThan() {
        List<Article> articles = articleRepository.findByIdLessThan(15);
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByIdBetween() {
        List<Article> articles = articleRepository.findByIdBetween(10, 15);
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByIdIn() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(10);
        List<Article> articles = articleRepository.findByIdIn(list);
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByCreateTimeAfter() {
        List<Article> articles = articleRepository.findByCreateTimeAfter(LocalDateTime.now().minusDays(1));
        articles.forEach(System.out::println);
    }
}
