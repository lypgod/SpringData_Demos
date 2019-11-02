package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext-es.xml")
//@ContextConfiguration(classes = TransportClientConfig.class)
public class ElasticsearchTemplateTest {
    @Resource
    private ElasticsearchTemplate template;
    @Resource
    private ArticleRepository articleRepository;

    // 通过SpringData ES技术向ElasticSearch数据库存储一条数据
    @Test
    public void testSave() {
        // 创建索引
        template.createIndex(Article.class);

        // 创建映射
        template.putMapping(Article.class);

        // 创建文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("sd-黑马程序员");
        article.setContext("sd-黑马程序员很棒");
        article.setHits(12);

        // 保存文档
        articleRepository.save(article);
    }

    // 修改
    @Test
    public void testUpdate() {
        // 判断数据库中是否有你指定的id的文档,如果没有,就进行保存,如果有,就进行更新
        Article article = new Article();
        article.setId(1);
        article.setTitle("sd-黑马程序员1");
        article.setContext("sd-黑马程序员很棒1");

        articleRepository.save(article);
    }

    // 删除
    @Test
    public void testDelete() {
        // 根据主键删除
        articleRepository.deleteById(1);
    }

    // 重新构建数据
    @Test
    public void makeData() {
        // 创建索引
        template.createIndex(Article.class);

        // 创建映射
        template.putMapping(Article.class);

        for (int i = 1; i <= 10; i++) {
            // 创建文档
            Article article = new Article();
            article.setId(i);
            article.setTitle("sd-黑马程序员" + i);
            article.setContext("sd-黑马程序员很棒" + i);
            article.setHits(100 + i);
            // 保存文档
            articleRepository.save(article);
        }
    }

    // 查询所有
    @Test
    public void testFindAll() {
        Iterable<Article> all = articleRepository.findAll();
        for (Article article : all) {
            System.out.println(article);
        }
    }

    // 主键查询
    @Test
    public void testFindById() throws Exception {
        Article article = articleRepository.findById(1).orElseThrow(() -> new Exception("Not found!"));
        System.out.println(article);
    }

    // 分页查询
    @Test
    public void testFindAllWithPage() {
        // 设置分页条件 page代表的页码,从0开始
        Pageable pageable = PageRequest.of(1, 3);

        Page<Article> page = articleRepository.findAll(pageable);

        for (Article article : page.getContent()) {
            System.out.println(article);
        }
    }

    // 排序查询
    @Test
    public void testFindAllWithSort() {
        // 设置排序条件
        Sort sort = Sort.by(Sort.Order.desc("hits"));

        Iterable<Article> all = articleRepository.findAll(sort);
        for (Article article : all) {
            System.out.println(article);
        }
    }

    // 分页+排序查询
    @Test
    public void testFindAllWithPageAndSort() {
        // 设置排序条件
        Sort sort = Sort.by(Sort.Order.desc("hits"));

        // 设置分页条件 page代表的页码,从0开始
        Pageable pageable = PageRequest.of(1, 3, sort);

        Page<Article> page = articleRepository.findAll(pageable);

        for (Article article : page.getContent()) {
            System.out.println(article);
        }
    }

    // 根据标题查询
    @Test
    public void testFindByTitle() {
        List<Article> articles = articleRepository.findByTitle("员");
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    // 根据标题查询
    @Test
    public void testFindByTitleOrContext() {
        List<Article> articles = articleRepository.findByTitleOrContext("程序员", "程序员");
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    // 根据标题查询
    @Test
    public void testFindByTitleOrContextWithPage() {
        // 设置排序条件
        Sort sort = Sort.by(Sort.Order.desc("hits"));

        // 设置分页条件 page代表的页码,从0开始
        Pageable pageable = PageRequest.of(1, 3, sort);

        List<Article> articles = articleRepository.findByTitleOrContext("程序员", "程序员", pageable);
        for (Article article : articles) {
            System.out.println(article);
        }
    }

}
