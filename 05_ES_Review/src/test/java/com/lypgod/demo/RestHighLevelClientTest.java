package com.lypgod.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lypgod.demo.model.entity.Article;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RestHighLevelClientTest {
    private RestHighLevelClient client;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void initClient() {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.2.10", 9200, "http"))
        );
    }

    @After
    public void closeClient() throws IOException {
        this.client.close();
    }

    @Test
    public void createDataTest() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("title", "high level createDataTest2");
        data.put("content", "high level createDataTest content2");
        data.put("hits", 778);

        IndexRequest indexRequest = new IndexRequest("lypgod-index").id("34").source(data);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println("id        -> " + indexResponse.getId());
        System.out.println("index     -> " + indexResponse.getIndex());
//        System.out.println("type      -> " + indexResponse.getType());
        System.out.println("version   -> " + indexResponse.getVersion());
        System.out.println("result    -> " + indexResponse.getResult());
        System.out.println("shardInfo -> " + indexResponse.getShardInfo());
    }

    @Test
    public void searchDataTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("lypgod-index");
        searchRequest.types("article");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "low level createDataTest"));
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = this.client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("搜索到 " + search.getHits().getTotalHits() + "条数据");

        search.getHits().forEach(hit -> {
            String json = hit.getSourceAsString();
            try {
                Article article = MAPPER.readValue(json, Article.class);
                System.out.println(article);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
