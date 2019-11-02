package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaQueryJpqlTest {
    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void testByJpqlTitleAndAuthor() {
        List<Article> articles = articleRepository.findByJpqlNumberedParams("标题1", "作者1");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByJpqlNamedParams() {
        List<Article> articles = articleRepository.findByJpqlNamedParams("标题1", "作者1");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByJpqlLike() {
        List<Article> articles = articleRepository.findByJpqlLike("标题");
        articles.forEach(System.out::println);
    }


    @Test
    public void testFindByJpqlSort() {
        List<Article> articles = articleRepository.findByJpqlSort("标题");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByJpqlPage() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Article> articles = articleRepository.findByJpqlPage(pageable, "标题");
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByJpqlIn() {
        List<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(10);

        List<Article> articles = articleRepository.findByJpqlIn(list);
        articles.forEach(System.out::println);
    }

    @Test
    public void testFindByJpqlObject() {
        Article articleParam = new Article();
        articleParam.setTitle("标题1");
        articleParam.setAuthor("作者1");

        List<Article> articles = articleRepository.findByJpqlObject(articleParam);
        articles.forEach(System.out::println);
    }


    @Test
    public void testFindByNativeQuery() {
        List<Article> articles = articleRepository.findByNativeQuery("标题1", "作者1");
        articles.forEach(System.out::println);
    }

}
