package com.lypgod.demo.springdataproject.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
@Data
@Accessors(chain = true)
public class Comment {
    @Id
    private String id;
    private Integer articleId;
    private String comment;
    private String nickname;
}