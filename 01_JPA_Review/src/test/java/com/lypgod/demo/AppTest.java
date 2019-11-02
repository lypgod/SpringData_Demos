package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class AppTest {
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void setUp() throws Exception {
        String persistenceUnitName = "mysql-persistence-unit";
        this.entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
        this.transaction = this.entityManager.getTransaction();
        this.transaction.begin();
    }

    @After
    public void tearDown() throws Exception {
        this.transaction.commit();
        this.entityManager.close();
    }

    @Test
    public void testSave() {
        Article article = new Article().setAuthor("lypgod").setTitle("新文章").setCreateTime(LocalDateTime.now());
        this.entityManager.persist(article);
    }

    @Test
    public void testFind() {
        System.out.println(this.entityManager.find(Article.class, 2));
    }

    @Test
    public void testUpdate() {
        Article article = this.entityManager.find(Article.class, 2);
        article.setTitle("新文章11");
        this.entityManager.merge(article);
    }

    @Test
    public void testDelete() {
        Article article = entityManager.find(Article.class, 3);
        entityManager.remove(article);
    }
}
