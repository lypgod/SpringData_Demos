package com.lypgod.demo;

import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaQueryTest {
    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void testFindByTitle() {
        System.out.println(this.articleRepository.findByTitle("新文章11"));
    }
}
