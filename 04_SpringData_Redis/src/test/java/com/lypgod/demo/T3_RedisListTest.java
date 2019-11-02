package com.lypgod.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class T3_RedisListTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private ListOperations<String, String> operations = null;

    @Before
    public void init() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        operations = redisTemplate.opsForList();
    }

    //增加
    @Test
    public void testAdd() {
        //从左边添加一个元素
        operations.leftPush("students", "张三左");
        //从左边添加多个元素
        operations.leftPushAll("students", "李四左", "王五左", "赵六左");

        //从右边添加一个元素
        operations.rightPush("students", "张三右");
        //从右边添加多个元素
        operations.rightPushAll("students", "李四右", "王五右", "赵六右");
    }

    //查询
    @Test
    public void testFind() {
        // 根据key和索引进行查询
        // 0和正数代表从左边开始  0   1   2
        // 负数代表从右边开始    -1  -2  -3
        String student = operations.index("students", 1);
        System.out.println("students 1：" + student);

        String student1 = operations.index("students", -2);
        System.out.println("students -2：" + student1);

        // 范围查询
        // 根据key [start, end]  包括首尾
        List<String> students = operations.range("students", 0, -1);
        assert students != null;
        for (String s : students) {
            System.out.println(s);
        }
    }

    //删除
    @Test
    public void testRemove(){
        //从左边删除第一个元素
        String studentLeft = operations.leftPop("students");
        System.out.println("left popped：" + studentLeft);

        //从右边删除第一个元素
        String studentRight = operations.rightPop("students");
        System.out.println("right popped：" + studentRight);


        // count > 0：删除左边起第几个等于指定值的元素
        // count < 0：删除右边起第几个等于指定值的元素
        // count = 0：删除所有等于value的元素。
        // 删除左边起第二个王五
        operations.remove("students",2,"王五");
    }
}
