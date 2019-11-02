package com.lypgod.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T0_RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 测试:向redis保存一条数据
    @Test
    public void testSaveWithDefaultSerializer() {
        // 获取操作简单字符串类型数据的数据句柄
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        // operations.set("name", "lypgod");
        System.out.println(operations.get("name"));
    }

    @Test
    public void testSaveWithStringRedisSerializer() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 获取操作简单字符串类型数据的数据句柄
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        // operations.set("name11", "very good");
        operations.append("name11", "lypgod");
    }

    @Test
    public void testSaveWithStringRedisSerializerXml() {
        // applicationContext-redis.xml中配置
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("name3", "heima3");
    }
}
