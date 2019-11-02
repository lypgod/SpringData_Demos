package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import com.lypgod.demo.model.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-jpa.xml")
public class SpringDataJpaQuerySpecificationTest {
    @Resource
    private ArticleRepository articleRepository;

    // 按照标题和作者进行查询,以不为空的属性作为查询条件
    @Test
    public void testFindAll() {
        //模拟从从外边传入的变量
        String title = "标题3";
        String author = "作者3";

        List<Article> articles = articleRepository.findAll(new Specification<Article>() {
            /**
             * @param root  代表实体对象,我们可以通过它获取属性值
             * @param cq    用于生成SQL语句
             * @param cb    用于拼接查询条件
             * @return Predicate
             */
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(title)) {
                    //拼接作为查询条件
                    Predicate predicate = cb.equal(root.get("title").as(String.class), title);
                    list.add(predicate);
                }
                if (!StringUtils.isEmpty(author)) {
                    //拼接作为查询条件
                    Predicate predicate = cb.equal(root.get("author").as(String.class), author);
                    list.add(predicate);
                }

                return cb.and(list.toArray(new Predicate[]{}));
            }
        });

        articles.forEach(System.out::println);
    }


    @Test
    public void testFindAllWithPage() {
        String title = "";
        String author = "";

        Pageable pageable = PageRequest.of(0, 3);

        Page<Article> page = articleRepository.findAll((Specification<Article>) (root, cq, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(title)) {
                //拼接作为查询条件
                Predicate predicate = cb.equal(root.get("title").as(String.class), title);
                list.add(predicate);
            }
            if (!StringUtils.isEmpty(author)) {
                //拼接作为查询条件
                Predicate predicate = cb.equal(root.get("author").as(String.class), author);
                list.add(predicate);
            }

            return cb.and(list.toArray(new Predicate[]{}));
        }, pageable);

        page.getContent().forEach(System.out::println);
    }


    @Test
    public void testFindAllWithPageAndSort() {
        String title = "";
        String author = "";

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("id")));

        Page<Article> page = articleRepository.findAll((Specification<Article>) (root, cq, cb) -> {

            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(title)) {
                //拼接作为查询条件
                Predicate predicate = cb.equal(root.get("title").as(String.class), title);
                list.add(predicate);
            }
            if (!StringUtils.isEmpty(author)) {
                //拼接作为查询条件
                Predicate predicate = cb.equal(root.get("author").as(String.class), author);
                list.add(predicate);
            }

            return cb.and(list.toArray(new Predicate[]{}));
        }, pageable);

        page.getContent().forEach(System.out::println);
    }

}
