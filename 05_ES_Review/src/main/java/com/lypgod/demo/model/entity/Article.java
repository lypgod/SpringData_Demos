package com.lypgod.demo.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {
    private String title;
    private String content;
    private Integer hits;
}