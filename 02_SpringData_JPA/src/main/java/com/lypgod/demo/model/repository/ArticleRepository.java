package com.lypgod.demo.model.repository;

import com.lypgod.demo.model.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
    /**
     * findByTitle
     *
     * @param title String
     * @return List<Article>
     */
    List<Article> findByTitle(String title);

    /**
     * findByTitleLike
     *
     * @param title String
     * @return List<Article>
     */
    List<Article> findByTitleLike(String title);

    /**
     * findByTitleAndAuthor
     *
     * @param title  String
     * @param author String
     * @return List<Article>
     */
    List<Article> findByTitleAndAuthor(String title, String author);

    /**
     * findByIdLessThan
     *
     * @param id Integer
     * @return List<Article>
     */
    List<Article> findByIdLessThan(Integer id);

    /**
     * findByIdBetween
     *
     * @param start Integer
     * @param end   Integer
     * @return List<Article>
     */
    List<Article> findByIdBetween(Integer start, Integer end);

    /**
     * findByIdIn
     *
     * @param ids List<Integer>
     * @return List<Article>
     */
    List<Article> findByIdIn(List<Integer> ids);

    /**
     * findByCreateTimeAfter
     *
     * @param dateTime LocalDateTime
     * @return List<Article>
     */
    List<Article> findByCreateTimeAfter(LocalDateTime dateTime);

    /**
     * JPQL:类似于SQL语句,但是要使用实体类名代替表名,使用属性名代替字段名[面向对象查询]
     * 展示位置参数绑定[按照title和author查询]
     * 占位符从1开始
     *
     * @param title  String
     * @param author String
     * @return List<Article>
     */
    @Query("from Article a where a.title = ?1 and a.author =?2")
    List<Article> findByJpqlNumberedParams(String title, String author);

    /**
     * 展示名字参数绑定
     *
     * @param title  String
     * @param author String
     * @return List<Article>
     */
    @Query("from Article a where a.title = :title and a.author = :authors")
    List<Article> findByJpqlNamedParams(@Param("title") String title, @Param("authors") String author);

    /**
     * 展示like模糊查询
     *
     * @param title String
     * @return List<Article>
     */
    @Query("from Article a where a.title like %:title%")
    List<Article> findByJpqlLike(@Param("title") String title);

    /**
     * 展示排序查询
     *
     * @param title String
     * @return List<Article>
     */
    @Query("from Article a where a.title like %:title% order by a.id desc ")
    List<Article> findByJpqlSort(@Param("title") String title);

    /**
     * 展示分页查询
     *
     * @param pageable Pageable
     * @param title    String
     * @return List<Article>
     */
    @Query("from Article a where a.title like %:title%")
    List<Article> findByJpqlPage(Pageable pageable, @Param("title") String title);

    /**
     * 展示传入集合参数查询
     *
     * @param ids List<Integer>
     * @return List<Article>
     */
    @Query("from Article a where a.id in :ids")
    List<Article> findByJpqlIn(@Param("ids") List<Integer> ids);

    /**
     * 展示传入Bean进行查询（SPEL表达式查询）
     *
     * @param article Article
     * @return List<Article>
     */
    @Query("from Article a where a.title = :#{#article.title} and a.author = :#{#article.author}")
    List<Article> findByJpqlObject(@Param("article") Article article);


    /**
     * 本地SQL查询
     *
     * @param title  String
     * @param author String
     * @return List<Article>
     */
    @Query(value = "select * from article a where a.title = ?1 and a.author =?2", nativeQuery = true)
    List<Article> findByNativeQuery(String title, String author);

}
