package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaTest {
    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void testSave() {
        Article article = new Article().setAuthor("lypgod").setTitle("JPA新文章").setCreateTime(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    @Test
    public void testFindAll() {
        this.articleRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        System.out.println(this.articleRepository.findById(4));
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Article article = this.articleRepository.findById(4).orElseThrow(() -> new NotFoundException("Not found"));
        article.setTitle("JPA新文章11");
        this.articleRepository.save(article);
    }

    @Test
    public void testDelete() {
        this.articleRepository.deleteById(4);
    }
}
