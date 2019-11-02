package com.lypgod.demo.springdataproject;

import com.lypgod.demo.springdataproject.model.entity.Article;
import com.lypgod.demo.springdataproject.model.entity.ArticleData;
import com.lypgod.demo.springdataproject.model.entity.Comment;
import com.lypgod.demo.springdataproject.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageTest {
    @Resource
    private ArticleService articleService;

    // 保存文章
    @Test
    public void testSaveArticle() {
        // 准备测试数据
        ArticleData articleData = new ArticleData()
                .setContent("~~黑马程序员专注于IT培训,Java培训,人工智能培训,Python培训,大数据培训," +
                        "区块链培训,UI设计培训,PHP培训,Web前端培训,软件测试培训,产品经理培训，并提供Java培训," +
                        "大数据培训,区块链培训,UI设计培训,PHP培训,软件测试培训,产品经理培训等服务。");

        Article article = new Article()
                .setAuthor("~~黑马程序员")
                .setTitle("~~黑马程序员介绍");

        // 建立两者关系
        article.setArticleData(articleData);
        articleData.setArticle(article);

        articleService.saveArticle(article);
    }

    // 更新文章
    @Test
    public void testUpdateArticle() throws Exception {
        Article article = articleService.getArticleById(2);
        article.setTitle("--黑马程序员介绍");
        article.setAuthor("--黑马程序员");
        article.getArticleData().setContent("---黑马程序员专注于IT培训,Java培训,人工智能培训,Python培训,大数据培训," +
                "区块链培训,UI设计培训,PHP培训,Web前端培训,软件测试培训,产品经理培训，并提供Java培训," +
                "大数据培训,区块链培训,UI设计培训,PHP培训,软件测试培训,产品经理培训等服务。");

        articleService.updateArticle(article);
    }


    // 删除文章
    @Test
    public void testDeleteArticle() {
        articleService.deleteArticleById(2);
    }

    // 添加评论
    @Test
    public void testSaveComment(){
        Comment comment = new Comment()
                .setId(UUID.randomUUID().toString())
                .setArticleId(3)
                .setComment("~~黑马程序员真棒!!!")
                .setNickname("~~黑马程序员");
        articleService.saveComment(comment);
    }

    // 删除评论
    @Test
    public void testDeleteComment(){
        articleService.deleteCommentById("95ebb224-ac83-48fe-82fa-2b64ac79ba6f");
    }
}
