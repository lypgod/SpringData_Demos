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
public class SpringDataJpaCUDTest {
    @Resource
    private ArticleRepository articleRepository;

    //保存
    @Test
    public void testSave() {
        Article article = new Article();
        article.setTitle("黑马程序员1");
        article.setAuthor("黑马2");
        article.setCreateTime(LocalDateTime.now());

        //保存一个实体
        articleRepository.save(article);

        //保存一个实体,并且立即刷新缓存
        //articleRepository.saveAndFlush(article);
    }

    //保存多个
    @Test
    public void testSaveAll() {
        Article article1 = new Article();
        article1.setTitle("黑马程序员1");
        article1.setAuthor("黑马1");
        article1.setCreateTime(LocalDateTime.now());

        Article article2 = new Article();
        article2.setTitle("黑马程序员2");
        article2.setAuthor("黑马2");
        article2.setCreateTime(LocalDateTime.now());

        Article article3 = new Article();
        article3.setTitle("黑马程序员3");
        article3.setAuthor("黑马3");
        article3.setCreateTime(LocalDateTime.now());

        List<Article> list = new ArrayList<>();
        list.add(article1);
        list.add(article2);
        list.add(article3);

        //保存多个实体
        articleRepository.saveAll(list);
    }

    //删除
    @Test
    public void testDeleteOne() {
        //1  根据主键删除
        //articleRepository.deleteById(13);

        //2 根据实体删除,但是这个实体必须要有主键
        Article article = new Article();
        article.setId(13);

        articleRepository.delete(article);
    }

    //删除
    @Test
    public void testDeleteAll() {
        //1 删除所有  先查询--再一条条的删除
        //articleRepository.deleteAll();

        //2 删除所有  一下子删除所有记录
        //articleRepository.deleteAllInBatch();

        Article article1 = new Article();
        article1.setId(24);

        Article article2 = new Article();
        article2.setId(22);

        List<Article> list = new ArrayList<>();
        list.add(article1);
        list.add(article2);

        //3 批量删除指定数据  一条语句搞定
        //articleRepository.deleteInBatch(list);

        //4 先一条条的查,然后再一条条的删除
        articleRepository.deleteAll(list);
    }


    @Test
    public void addData() {
        List<Article> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            list.add(new Article().setAuthor("作者" + i).setTitle("标题" + i).setCreateTime(LocalDateTime.now()));
        }
        articleRepository.saveAll(list);
    }
}
