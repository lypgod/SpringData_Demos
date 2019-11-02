package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "article")
public class ArticleData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    /**
     * 让这个实体维护关系
     * name                    当前表中的外键名
     * referencedColumnName    指向的对方表中的主键名
     */
    @OneToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id", unique = true)
    private Article article;
}
