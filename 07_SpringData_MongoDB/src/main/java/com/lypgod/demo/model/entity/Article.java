package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 使用@Document建立的是实体类和collection的关系
 */
@Document("article")
@Data
@Accessors(chain = true)
public class Article {
    @Id
    private Integer id;
    private String name;
    private String content;
    private Integer hits;
}
