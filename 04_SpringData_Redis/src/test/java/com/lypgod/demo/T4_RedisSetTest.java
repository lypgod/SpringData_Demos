package com.lypgod.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T4_RedisSetTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private SetOperations<String, String> operations = null;

    @Before
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        operations = redisTemplate.opsForSet();
    }

    // 增加
    @Test
    public void testAdd() {
        operations.add("students", "张三", "李四", "王五", "张三");
    }

    // 查询
    @Test
    public void testFind() {
        // 查询所有元素
        Set<String> students = operations.members("students");
        assert students != null;
        students.forEach(System.out::println);

        System.out.println("-----------------------");

        // 随机获取一个元素
        String student = operations.randomMember("students");
        System.out.println("random student: " + student);

        System.out.println("-----------------------");

        // 随机多个元素[可能会重复]
        List<String> randomMembers = operations.randomMembers("students", 2);
        assert randomMembers != null;
        randomMembers.forEach(System.out::println);
    }

    // 删除
    @Test
    public void testRemove() {
        // 移除元素,并返回移除成功个数
        Long count = operations.remove("students", "张三", "王五", "赵六");
        System.out.println(count);
    }

    @Test
    public void testRandomRemove() {
        // 随机移除指定集合中的多少个元素
        List<String> students = operations.pop("students", 2);
        assert students != null;
        students.forEach(System.out::println);
    }

    //多集合操作
    @Test
    public void testMoreSet() {
        operations.add("group1", "张三", "李四", "王五");
        operations.add("group2", "张三", "李四", "赵六");

        // 取交集
        System.out.println("----------- intersect ------------");
        Set<String> intersect = operations.intersect("group1", "group2");
        assert intersect != null;
        intersect.forEach(System.out::println);

        // 取并集
        System.out.println("----------- union ------------");
        Set<String> union = operations.union("group1", "group2");
        assert union != null;
        union.forEach(System.out::println);

        // 取差集[第一个集合中存在,但是在第二个集合中不存在的元素]
        System.out.println("----------- difference ------------");
        Set<String> difference = operations.difference("group1", "group2");
        assert difference != null;
        difference.forEach(System.out::println);
    }

}
