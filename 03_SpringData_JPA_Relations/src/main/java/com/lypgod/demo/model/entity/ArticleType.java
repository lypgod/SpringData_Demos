package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "articles")
@EqualsAndHashCode(of = "id")
public class ArticleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            //代表中间表名称
            name = "article_type",
            //中间表的外键对应到当前表的主键名称
            joinColumns = {@JoinColumn(name = "typeId", referencedColumnName = "id")},
            //中间表的外键对应到对方表的主键名称
            inverseJoinColumns = {@JoinColumn(name = "articleId", referencedColumnName = "id")}
    )
    private Set<Article> articles = new HashSet<>(8);

}
