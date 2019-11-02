package com.lypgod.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T1_RedisStringTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> operations = null;

    @Before
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        operations = redisTemplate.opsForValue();
    }


    @Test
    public void testSet() {
        // 1.向数据库中保存name--lypgod
        operations.set("name", "lypgod");

        // 2.相关数据库保存name1--lypgod1  有效时间为10s
        operations.set("name1", "lypgod1", 10, TimeUnit.SECONDS);

        // 3.替换 lypgod ---> lyXXod  offset 索引位置是从0开始
        operations.set("name", "XX", 2);

        // 4.当key不存在的时候,执行保存操作; 当key存在的时候,什么都不做
        operations.setIfAbsent("name1", "lypgod");

        // 5.批量保存
        Map<String, String> map = new HashMap<>();
        map.put("name2", "lypgod2");
        map.put("name3", "lypgod3");
        map.put("name4", "lypgod4");
        operations.multiSet(map);

        // 6.追加 当key存在时,会执行追加操作;当key不存在时,会执行保存操作
        operations.append("name5", "lypgod");
    }

    @Test
    public void testGet() {
        // 1.根据key获取value
        String value = operations.get("name");
        System.out.println(value);

        // 2.首先根据key获取value,然后再根据value进行截取,从start位置截取到end位置[包含start和end]
        String value2 = operations.get("name", 5, 7);
        System.out.println(value2);

        // 3.批量获取
        List<String> keys = new ArrayList<>();
        keys.add("name2");
        keys.add("name3");
        keys.add("name4");
        List<String> values = operations.multiGet(keys);
        Assert.notNull(values, "values is null");
        values.forEach(System.out::println);

        // 4.根据key获取value的长度
        Long size = operations.size("name");
        System.out.println(size);
    }

    //自增
    @Test
    public void testIncrement() {
        operations.set("age", "18");

        //自增1--->19
        operations.increment("age");
        System.out.println(operations.get("age"));

        //自增5--->24
        operations.increment("age", 5);
        System.out.println(operations.get("age"));

        //自减--->23
        operations.decrement("age");
        System.out.println(operations.get("age"));
    }

    //删除
    @Test
    public void testDelete() {
        // 1.单个删除
        redisTemplate.delete("name");

        // 2.批量删除
        List<String> keys = new ArrayList<>();
        keys.add("name2");
        keys.add("name3");
        keys.add("name4");
        redisTemplate.delete(keys);
    }

}
