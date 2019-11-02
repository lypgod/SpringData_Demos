package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Article implements Serializable {
    private Integer id;
    private String author;
    private LocalDateTime createTime;
    private String title;
}
