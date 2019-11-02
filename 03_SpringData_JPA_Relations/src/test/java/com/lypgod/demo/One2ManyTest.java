package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.entity.ArticleComment;
import com.lypgod.demo.model.repository.ArticleCommentRepository;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class One2ManyTest {
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleCommentRepository articleCommentRepository;

    //保存
    @Test
    public void testSave() {
        //创建文章对象
        Article article = new Article()
                .setTitle("黑马好文章")
                .setAuthor("黑马")
                .setCreateTime(LocalDateTime.now());

        //创建文章评论对象
        ArticleComment comment1 = new ArticleComment();
        comment1.setComment("真不错");
        ArticleComment comment2 = new ArticleComment();
        comment2.setComment("挺好的");

        //建立两个对象间的关系
        article.addComment(comment1);
        article.addComment(comment2);

        //保存操作
        articleRepository.save(article);
    }

    @Test
    public void testOrphanRemoval() throws Exception {
        Article article = articleRepository.findById(26).orElseThrow(() -> new Exception("not Found!"));
        ArticleComment articleComment = articleCommentRepository.findById(1).orElseThrow(() -> new Exception("not Found!"));
        article.removeComment(articleComment);
        articleRepository.save(article);
    }
}
