package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.entity.ArticleData;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class One2OneTest {
    @Resource
    private ArticleRepository articleRepository;


    //保存
    @Test
    public void testSave() {
        //创建文章对象
        Article article = new Article()
                .setTitle("黑马好文章")
                .setAuthor("黑马")
                .setCreateTime(LocalDateTime.now());

        //创建文章内容对象
        ArticleData articleData = new ArticleData();
        articleData.setContent("真是一篇好文章");


        //建立两个对象间的关系
        article.setArticleData(articleData);
        articleData.setArticle(article);

        //保存操作
        articleRepository.save(article);
    }

    @Test
    public void testOrphanRemoval() throws Exception {
        Article article = articleRepository.findById(25).orElseThrow(() -> new Exception("not Found!"));
        article.setArticleData(null);
        articleRepository.save(article);
    }
}
