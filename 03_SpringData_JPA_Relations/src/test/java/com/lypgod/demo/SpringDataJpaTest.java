package com.lypgod.demo;

import com.lypgod.demo.model.entity.ArticleType;
import com.lypgod.demo.model.repository.ArticleRepository;
import com.lypgod.demo.model.repository.ArticleTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaTest {
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleTypeRepository articleTypeRepository;

    @Test
    public void testFindAllArticles() {
        this.articleRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testFindAllArticleTypes() {
        List<ArticleType> all = this.articleTypeRepository.findAll();
        for (ArticleType articleType : all) {
            System.out.println(articleType);
            articleType.getArticles().forEach(e -> System.out.println(e.getId()));
        }
    }
}
