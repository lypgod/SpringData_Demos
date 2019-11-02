package com.lypgod.demo.springdataproject.model.repository;

import com.lypgod.demo.springdataproject.model.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    /**
     * 命名规则查询:按照aid查询到一个文章的所有评论
     *
     * @param articleId Integer
     * @return List<Comment>
     */
    List<Comment> findAllByArticleId(Integer articleId);
}