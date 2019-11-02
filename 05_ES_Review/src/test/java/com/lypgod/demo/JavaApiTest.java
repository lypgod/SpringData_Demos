package com.lypgod.demo;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class JavaApiTest {
    private TransportClient client = null;

    // 创建连接
    @Before
    public void initClient() {
        try {
            Settings settings = Settings.builder().put("cluster.name", "my-application").build();
            this.client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.2.10"), 9300));
        } catch (UnknownHostException e) {
            System.out.println("初始化失败....");
            e.printStackTrace();
        }
    }

    // 关闭连接
    @After
    public void closeClient() {
        this.client.close();
    }

    // 创建索引
    @Test
    public void testCreateIndex() {
        this.client.admin().indices().prepareCreate("lypgod-index").get();
    }

    // 删除索引,可以一次性删除多个
    @Test
    public void testDeleteIndex() {
        this.client.admin().indices().prepareDelete("lypgod", "lypgod-index").get();
    }

    // 创建映射
    @Test
    public void testCreateMapping() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")

                .startObject("title")
                .field("type", "text")
                .field("store", "true")
                .field("analyzer", "ik_smart")
                .endObject()

                .startObject("content")
                .field("type", "text")
                .field("store", "true")
                .field("analyzer", "ik_smart")
                .endObject()

                .startObject("hits")
                .field("type", "long")
                .field("store", "true")
                .endObject()

                .endObject()
                .endObject()
                .endObject();

        // 创建映射(表结构)
        PutMappingRequest mapping = Requests.putMappingRequest("lypgod-index") // 指定索引(库)
                .type("article") // 指定类型(表)
                .source(builder);
        this.client.admin().indices().putMapping(mapping).get();
    }

    // 创建文档
    @Test
    public void testCreateDocument() {
        // 组装数据
        Map<String, Object> map = new HashMap<>();
        map.put("title", "黑马程序员");
        map.put("content", "黑马程序员其实很低调");
        map.put("hits", 100);

        //创建文档
        this.client.prepareIndex("lypgod-index", "article", "1").setSource(map).get();
    }

    //修改文档
    @Test
    public void testUpdateDocument() {
        //组装数据
        Map<String, Object> map = new HashMap<>();
        map.put("title", "传智播客2");
        map.put("content", "传智播客很低调2");
        map.put("hits", 200);

        //修改文档
        this.client.prepareUpdate("lypgod-index", "article", "1").setDoc(map).get();
    }


    //删除文档
    @Test
    public void testDeleteDocument() {
        this.client.prepareDelete("lypgod-index", "article", "1").get();
    }

    //创建文档
    @Test
    public void testCreateDocuments() {
        for (int i = 1; i <= 20; i++) {
            //组装数据
            Map<String, Object> map = new HashMap<>();
            map.put("title", "黑马程序员" + i);
            map.put("content", "黑马程序员其实很低调" + i);
            map.put("hits", 100 + i);

            //创建文档
            this.client.prepareIndex("lypgod-index", "article", String.valueOf(i)).setSource(map).get();
        }
    }

    //查询所有
    @Test
    public void testFindAll() {
        // 1.设置index type  查询条件,返回一个查询结果对象
        SearchResponse searchResponse = this.client
                .prepareSearch("lypgod-index")
                .setTypes("article") // 设置index和type,允许传入多个
                .setQuery(QueryBuilders.matchAllQuery()) // 设置查询条件 :查询所有
                .get();

        // 2.检索的命中对象
        SearchHits hits = searchResponse.getHits();

        // 3.获取查询结果数
        System.out.println("总共查询到" + hits.getTotalHits() + "条记录");

        // 4.获取结果
        hits.forEach(hit -> System.out.println(hit.getSourceAsString()));
    }

    @Test
    public void testMultiSearch() {
        SearchRequestBuilder srb1 = client
                .prepareSearch("lypgod-index")
                .setQuery(QueryBuilders.queryStringQuery("黑马程序员"))
                .setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch("lypgod-index")
                .setQuery(QueryBuilders.matchQuery("title", "黑马程序员15"))
                .setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits().value;
            response.getHits().forEach(hit -> System.out.println(hit.getSourceAsString()));
        }
        System.out.println("---" + nbHits);
    }

    //根据title查询
    @Test
    public void testFindByTitle() {
        // QueryBuilders.termQuery("属性","值[分词之后存在]")
        SearchResponse searchResponse = this.client
                .prepareSearch("lypgod-index")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("title","黑马程序员"))//设置查询条件 :根据title查询
                .get();

        //2 检索的命中对象
        SearchHits hits = searchResponse.getHits();

        //3 获取查询结果数
        System.out.println("总共查询到" + hits.getTotalHits() + "条记录");

        //4 获取结果
        hits.forEach(hit -> System.out.println(hit.getSourceAsString()));
    }

    //分页和排序
    @Test
    public void testFindAllWithPageAndSort() {
        SearchResponse searchResponse = this.client
                .prepareSearch("lypgod-index")
                .setTypes("article")
                .setQuery(QueryBuilders.matchAllQuery())
                .setFrom(0)
                .setSize(10)
                .addSort("hits", SortOrder.ASC)
                .get();

        SearchHits hits = searchResponse.getHits();
        System.out.println("总共查询到" + hits.getTotalHits() + "条记录");
        hits.forEach(hit -> System.out.println(hit.getSourceAsString()));
    }

}
