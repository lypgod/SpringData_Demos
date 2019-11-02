package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.entity.ArticleType;
import com.lypgod.demo.model.repository.ArticleRepository;
import com.lypgod.demo.model.repository.ArticleTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class Many2ManyTest {
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleTypeRepository articleTypeRepository;

    //保存
    @Test
    public void testSave() throws Exception {
        //创建文章对象
        Article article1 = new Article()
                .setTitle("黑马好文章11")
                .setAuthor("黑马11")
                .setCreateTime(LocalDateTime.now());
        articleRepository.save(article1);

        Article article2 = new Article()
                .setTitle("黑马好文章22")
                .setAuthor("黑马22")
                .setCreateTime(LocalDateTime.now());
        articleRepository.save(article2);

        //创建文章类型对象
//        ArticleType typeSport = new ArticleType();
//        typeSport.setName("运动");
//        articleTypeRepository.save(typeSport);
//        ArticleType typeNews = new ArticleType();
//        typeNews.setName("新闻");
//        articleTypeRepository.save(typeNews);
//        ArticleType typeEntertainment = new ArticleType();
//        typeEntertainment.setName("娱乐");
//        articleTypeRepository.save(typeEntertainment);
        ArticleType typeSport = articleTypeRepository.findById(3).orElseThrow(() -> new Exception("Not Found"));
        ArticleType typeNews = articleTypeRepository.findById(4).orElseThrow(() -> new Exception("Not Found"));
        ArticleType typeEntertainment = articleTypeRepository.findById(5).orElseThrow(() -> new Exception("Not Found"));

        //建立两个对象间的关系
        article1.addType(typeSport);
        article1.addType(typeNews);
        articleRepository.save(article1);

        article2.addType(typeNews);
        article2.addType(typeEntertainment);
        articleRepository.save(article2);
    }
}
