package com.lypgod.demo.springdataproject.service;

import com.lypgod.demo.springdataproject.model.entity.Article;
import com.lypgod.demo.springdataproject.model.entity.Comment;
import com.lypgod.demo.springdataproject.model.entity.EsArticle;
import com.lypgod.demo.springdataproject.model.repository.ArticleRepository;
import com.lypgod.demo.springdataproject.model.repository.CommentRepository;
import com.lypgod.demo.springdataproject.model.repository.EsArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleService {
    private static final String ARTICLES_KEY = "articles";

    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private RedisTemplate<String, List<Article>> redisTemplate;
    @Resource
    private ElasticsearchOperations elasticsearchOperations;
    @Resource
    private EsArticleRepository esArticleRepository;
    @Resource
    private CommentRepository commentRepository;

    // ------------------- Article方法 -------------------

    public Article getArticleById(Integer id) throws Exception {
        return articleRepository.findById(id).orElseThrow(() -> new Exception("Article with id " + id + " not found!"));
    }

    public void saveArticle(Article article) {
        // 向mysql保存文章和文章详情
        articleRepository.save(article);

        // 清空redis缓存
        redisTemplate.delete(ARTICLES_KEY);

        // 向ES中保存数据
        EsArticle esArticle = new EsArticle()
                .setId(article.getId())
                .setAuthor(article.getAuthor())
                .setTitle(article.getTitle())
                .setContent(article.getArticleData().getContent())
                .setCreateTime(article.getCreateTime());
        esArticleRepository.save(esArticle);
    }

    public void updateArticle(Article article) {
        // 更新article
        articleRepository.save(article);

        // 清空redis缓存
        redisTemplate.delete(ARTICLES_KEY);

        //向ES中保存数据
        EsArticle esArticle = new EsArticle()
                .setId(article.getId())
                .setAuthor(article.getAuthor())
                .setTitle(article.getTitle())
                .setContent(article.getArticleData().getContent())
                .setCreateTime(article.getCreateTime());
        esArticleRepository.save(esArticle);
    }

    public void deleteArticleById(Integer id) {
        // 删除article
        articleRepository.deleteById(id);

        // 删除mongodb中相关的评论
        List<Comment> commentsByArticleId = commentRepository.findAllByArticleId(id);
        commentRepository.deleteAll(commentsByArticleId);

        // 清空redis
        redisTemplate.delete(ARTICLES_KEY);

        // 删除ES中的数据
        esArticleRepository.deleteById(id);
    }

    public List<Article> findHotArticleList() {
        // 先从redis中获取
        List<Article> articles = redisTemplate.opsForValue().get(ARTICLES_KEY);

        // 如果redis中没有,去数据库中查询,查询到以后,要存入redis
        if (articles == null) {
            //设置分页排序条件
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createTime")));
            Page<Article> page = articleRepository.findAll(pageable);
            articles = page.getContent();
            // 将结果存入redis
            redisTemplate.opsForValue().set(ARTICLES_KEY, articles);
        }

        // 将结果转成List返回
        return articles;
    }

    public List<EsArticle> search(Integer pageNum, Integer pageSize, String keyword) {
        //设置分页条件
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esArticleRepository.findByTitleOrContent(keyword, keyword, pageable);
    }

    // ------------------- Comment方法 -------------------

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findCommentsByArticleId(Integer articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    // ------------------- 测试连接方法 -------------------

    public Article saveArticleDb(Article article) {
        return articleRepository.save(article);
    }

    public void saveArticlesRedis(List<Article> articles) {
        redisTemplate.opsForValue().set(ARTICLES_KEY, articles);
    }

    public List<Article> getArticlesRedis() {
        return redisTemplate.opsForValue().get(ARTICLES_KEY);
    }

    public String saveArticleEsOperations(EsArticle esArticle) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(esArticle.getId().toString())
                .withObject(esArticle)
                .build();
        return elasticsearchOperations.index(indexQuery);
    }

    public EsArticle saveArticleEsRepository(EsArticle esArticle) {
        return esArticleRepository.save(esArticle);
    }

    public EsArticle findEsArticle(String id) {
        return elasticsearchOperations.queryForObject(GetQuery.getById(id), EsArticle.class);
    }

    public void saveCommentMongo(Comment comment) {
        commentRepository.save(comment);
    }
}
