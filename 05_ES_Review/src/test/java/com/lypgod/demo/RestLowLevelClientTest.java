package com.lypgod.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestLowLevelClientTest {
    private RestClient restClient = null;

    // 创建连接
    @Before
    public void initClient() {
        this.restClient = RestClient.builder(
                new HttpHost("192.168.2.10", 9200, "http"))
                .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(Node node) {
                        System.out.println("节点出错 -> " + node);
                    }
                })
                .build();
    }

    // 关闭连接
    @After
    public void closeClient() throws IOException {
        this.restClient.close();
    }

    // 查询集群状态
    @Test
    public void getInfoTest() throws IOException {
        Request request = new Request("GET", "/_cluster/state");
        request.addParameter("pretty", "true");

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void getDataTest() throws IOException {
        Request request = new Request("GET", "/lypgod-index/article/1");
        request.addParameter("pretty", "true");

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void createDataTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Request request = new Request("POST", "/lypgod-index/article");

        Map<String, Object> data = new HashMap<>();
        data.put("title", "low level createDataTest");
        data.put("content", "low level createDataTest content");
        data.put("hits", 666);

        request.setJsonEntity(mapper.writeValueAsString(data));

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void searchDataTest() throws IOException {
        Request request = new Request("POST", "/lypgod-index/article/_search");
        String searchJson = "{ \"query\": { \"match\": { \"title\": \"low level createDataTest\" } } }";
        request.setJsonEntity(searchJson);
        request.addParameter("pretty", "true");

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        String responseJson = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseJson);
        int total = jsonNode.get("hits").get("total").get("value").asInt();
        System.out.println(total);

        ArrayNode hits = (ArrayNode) jsonNode.get("hits").get("hits");
        hits.forEach(hit -> {
            System.out.println(hit.get("_source").get("title").asText());
        });
    }

}
