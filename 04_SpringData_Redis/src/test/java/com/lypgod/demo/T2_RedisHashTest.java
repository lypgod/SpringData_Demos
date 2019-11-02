package com.lypgod.demo;

import com.lypgod.demo.model.entity.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T2_RedisHashTest {
    @Resource
    private RedisTemplate<String, Article> redisTemplate;

    private HashOperations<String, String, Article> operations = null;

    @Before
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        operations = redisTemplate.opsForHash();
    }

    //保存
    @Test
    public void testPut() {
        Article article = new Article()
                .setId(1)
                .setTitle("黑马")
                .setAuthor("黑马程序员")
                .setCreateTime(LocalDateTime.now());
        operations.put("article", "1", article);
    }

    @Test
    public void testPutAll() {
        Article article1 = new Article()
                .setId(1)
                .setTitle("黑马11")
                .setAuthor("黑马程序员11")
                .setCreateTime(LocalDateTime.now());
        Article article2 = new Article()
                .setId(2)
                .setTitle("黑马22")
                .setAuthor("黑马程序员22")
                .setCreateTime(LocalDateTime.now());

        Map<String, Article> hashMap = new HashMap<>();
        hashMap.put("1", article1);
        hashMap.put("2", article2);
        operations.putAll("article", hashMap);
    }


    //获取
    @Test
    public void testGet() {
        // 判断Hash Key是否存在
        Boolean flag = operations.hasKey("article", "3");
        System.out.println(flag);

        // 根据Key和Hash Key获取操作
        Article article = operations.get("article", "2");
        System.out.println(article);

        //根据Key获取所有的Hash Key
        Set<String> articleKeySet = operations.keys("article");
        for (String key : articleKeySet) {
            System.out.println(key);
        }

        List<Article> articles = operations.values("article");
        for (Article art : articles) {
            System.out.println(art);
        }

        Map<String, Article> map = operations.entries("article");
        for (Map.Entry<String, Article> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    //删除
    @Test
    public void testDelete() {
        //当Hash中的数据全部被删除后,整个Hash就没了
        operations.delete("article", "1", "2");
    }
}
