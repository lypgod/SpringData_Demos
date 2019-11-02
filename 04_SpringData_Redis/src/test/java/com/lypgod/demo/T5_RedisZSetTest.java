package com.lypgod.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T5_RedisZSetTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String, String> operations = null;

    @Before
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        operations = redisTemplate.opsForZSet();
    }

    // 增加
    @Test
    public void testAdd() {
        operations.add("students", "张三", 100);
        operations.add("students", "李四", 60);
        operations.add("students", "王五", 80);
    }

    // 分数的增减
    @Test
    public void testScore() {
        // incrementScore 可以用来增减分数。增加就用正数，减少用负数
        // 增加分数
        operations.incrementScore("students", "李四", 30);
        // 减少分数
        operations.incrementScore("students", "王五", -70);
    }

    // 查询一个元素的信息
    @Test
    public void testFindOne() {
        // 查询一个元素的分数
        Double score = operations.score("students", "王五");
        System.out.println(score);

        // 查询一个元素在集合中的排名   排名从0开始
        Long rank = operations.rank("students", "张三");
        System.out.println(rank);
    }

    // 根据一个区间获得一个列表
    @Test
    public void testFindList() {
        // 根据排名区间来获取元素列表
        Set<String> students = operations.range("students", 0, 2);
        assert students != null;
        students.forEach(System.out::println);

        System.out.println("---------------------------------");

        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = operations.rangeWithScores("students", 0, 2);
        assert rangeWithScores != null;
        for (ZSetOperations.TypedTuple<String> tuple : rangeWithScores) {
            System.out.println(tuple.getValue() + "同学，得了" + tuple.getScore() + "分");
        }

        System.out.println("---------------------------------");

        // 根据分数区间来获取列表
        Set<String> rangeByScore = operations.rangeByScore("students", 60, 90);
        assert rangeByScore != null;
        rangeByScore.forEach(System.out::println);

        System.out.println("---------------------------------");

        Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores = operations.rangeByScoreWithScores("students", 60, 90);
        assert rangeByScoreWithScores != null;
        for (ZSetOperations.TypedTuple<String> tuple : rangeByScoreWithScores) {
            System.out.println(tuple.getValue() + "同学，得了" + tuple.getScore() + "分");
        }
    }

    // 统计
    @Test
    public void testCount() {
        // 统计一个集合中元素
        Long zCard = operations.zCard("students");
        System.out.println(zCard);

        // 根据一个分数区间统计元素数量
        Long count = operations.count("students", 50, 100);
        System.out.println(count);
    }


    // 删除
    @Test
    public void testRemove() {
        // 根据key-value删除  value允许传入多个
        operations.remove("students", "张三", "李四");

        // 根据排名区间删除
        operations.removeRange("students", 0, 1);

        // 根据分数区间删除
        operations.removeRangeByScore("students", 70, 100);
    }
}
