package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;
    private LocalDateTime createTime;
    private String title;

    /**
     * 1 声明类间关系
     * 声明主动放弃关系维护 mappedBy="当前类在对方类中的属性名"
     * 当保存Article的时候,同时保存ArticleData
    */
    @OneToOne(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private ArticleData articleData;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ArticleComment> comments = new ArrayList<>(0);

    public void addComment(ArticleComment comment) {
        this.comments.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(ArticleComment comment) {
        this.comments.remove(comment);
        comment.setArticle(null);
    }

    @ManyToMany(mappedBy = "articles", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<ArticleType> types = new HashSet<>(8);

    public void addType(ArticleType type) {
        this.types.add(type);
        type.getArticles().add(this);
    }

    public void removeType(ArticleType type) {
        this.types.remove(type);
        type.getArticles().remove(this);
    }
}
