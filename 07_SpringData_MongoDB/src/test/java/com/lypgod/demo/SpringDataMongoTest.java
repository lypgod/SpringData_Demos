package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mongo.xml")
public class SpringDataMongoTest {
    @Resource
    private ArticleRepository articleRepository;

    // 保存
    @Test
    public void testSave() {
        Article article = new Article()
                .setId(1)
                .setName("黑马程序员")
                .setContent("黑马程序员很低调")
                .setHits(100);

        articleRepository.save(article);
    }

    // 修改
    @Test
    public void testUpdate() {
        Article article = new Article()
                .setId(1)
                .setName("黑马程序员2")
                .setContent("黑马程序员很低调2")
                .setHits(200);

        articleRepository.save(article);
    }

    // 删除
    @Test
    public void testDelete() {
        articleRepository.deleteById(1);
    }

    // 做数据
    @Test
    public void makeData() {
        for (int i = 1; i <= 10; i++) {
            Article article = new Article();
            article.setId(i);
            article.setName("黑马程序员" + i);
            article.setContent("黑马程序员很低调" + i);
            article.setHits(100 + i);

            articleRepository.save(article);
        }
    }

    // 查询所有
    @Test
    public void testFindAll() {
        articleRepository.findAll().forEach(System.out::println);
    }

    // 主键查询
    @Test
    public void testFindById() throws Exception {
        System.out.println(articleRepository.findById(1).orElseThrow(() -> new Exception("Not found!")));
    }

    // 分页和排序
    @Test
    public void testFindAllWithPageAndSort() {
        //设置排序条件
        Sort sort = Sort.by(Sort.Order.desc("hits"));
        //设置分页条件
        Pageable pageable = PageRequest.of(1, 3, sort);

        articleRepository.findAll(pageable).getContent().forEach(System.out::println);
    }

    // 根据标题查询
    @Test
    public void testFindByName() {
        articleRepository.findByNameLike("黑马程序员1").forEach(System.out::println);
    }

    // 根据标题查询
    @Test
    public void testFindByHitsGreaterThan() {
        articleRepository.findByHitsGreaterThan(105).forEach(System.out::println);
    }

}
