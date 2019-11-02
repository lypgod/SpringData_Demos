package com.lypgod.demo.springdataproject;

import com.lypgod.demo.springdataproject.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewTest {
    @Resource
    private ArticleService articleService;

    // 最新文章列表
    @Test
    public void findHotArticleListTest() {
        articleService.findHotArticleList().forEach(System.out::println);
    }

    // 根据文章获取评论
    @Test
    public void testFindCommentsByAid() {
        articleService.findCommentsByArticleId(3).forEach(System.out::println);
    }

    //文章全文检索
    @Test
    public void testSearch() {
        articleService.search(0, 10, "程序员").forEach(System.out::println);
    }
}