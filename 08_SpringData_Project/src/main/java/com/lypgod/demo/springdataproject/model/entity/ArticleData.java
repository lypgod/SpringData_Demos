package com.lypgod.demo.springdataproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
@ToString(exclude = "article")
public class ArticleData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @OneToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id", unique = true)
    @JsonIgnore
    private Article article;
}