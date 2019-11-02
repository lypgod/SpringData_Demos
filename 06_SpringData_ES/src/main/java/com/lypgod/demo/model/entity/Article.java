package com.lypgod.demo.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * indexName指定索引名称   type指定类型名称
 */
@Data
@Accessors(chain = true)
@Document(indexName = "lypgod-index", type = "article")
public class Article {
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer id;

    /**
     * index：是否设置分词  默认是true
     * analyzer：存储时使用的分词器
     * searchAnalyze：搜索时使用的分词器
     * store：是否存储 默认是false
     * type: 数据类型 默认值是FieldType.Auto
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String context;

    @Field(store = true, type = FieldType.Integer)
    private Integer hits;
}
