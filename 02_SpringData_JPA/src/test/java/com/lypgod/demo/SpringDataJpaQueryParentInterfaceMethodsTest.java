package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaQueryParentInterfaceMethodsTest {
    @Resource
    private ArticleRepository articleRepository;

    //查询所有--排序
    @Test
    public void testFindAllWithSort() {
        //按照id倒序排列
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Article> articles = articleRepository.findAll(sort);
        for (Article article : articles) {
            System.out.println(article);
        }
    }


    //查询所有--分页
    @Test
    public void testFindAllWithPage() {
        //处理分页条件
        //page   当前是第几页(从0开始)    size  每页大小
        Pageable pageable = PageRequest.of(0, 2);
        Page<Article> page = articleRepository.findAll(pageable);

        //总记录数  总页数  每页多少
        System.out.println("总记录数:" + page.getTotalElements());
        System.out.println("总页数:" + page.getTotalPages());
        System.out.println("每页条数:" + page.getSize());
        System.out.println("当前页数:" + page.getNumber());
        System.out.println("查询到记录数:" + page.getNumberOfElements());
        //当前页的元素
        List<Article> content = page.getContent();
        for (Article article : content) {
            System.out.println(article);
        }
    }


    //查询所有--分页+排序
    @Test
    public void testFindAllWithPageAndPage() {
        //按照aid倒序排列
        Sort sort = Sort.by(Sort.Order.desc("id"));

        //处理分页条件
        //page   当前是第几页(从0开始)    size  每页大小
        Pageable pageable = PageRequest.of(4, 5, sort);
        Page<Article> page = articleRepository.findAll(pageable);

        //总记录数  总页数  每页多少
        System.out.println("总记录数:" + page.getTotalElements());
        System.out.println("总页数:" + page.getTotalPages());
        System.out.println("每页条数:" + page.getSize());
        System.out.println("当前页数:" + page.getNumber());
        System.out.println("查询到记录数:" + page.getNumberOfElements());
        //当前页的元素
        List<Article> content = page.getContent();
        for (Article article : content) {
            System.out.println(article);
        }
    }

}
