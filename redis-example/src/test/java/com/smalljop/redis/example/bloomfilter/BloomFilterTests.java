package com.smalljop.redis.example.bloomfilter;

import com.smalljop.redis.example.hyperloglog.HyperLogLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BloomOperations;

/**
 * @description:
 * @author: smalljop
 * @create: 2020-01-09 14:17
 **/
@Slf4j
@SpringBootTest
public class BloomFilterTests {


    @Autowired
    BloomOperations bloomOperations;

    @Test
    public void test() {
        //创建过滤器
        bloomOperations.createFilter("bloom_filter", 0.1, 2000);
        //添加元素
        for (int i = 1; i < 200; i++) {
            bloomOperations.add("bloom_filter", String.valueOf(i));
        }
        //批量添加
        bloomOperations.addMulti("bloom_filter", "2000", "2001", "2003");
        //是否存在
        log.info("{}是否存在：{}", 2000, bloomOperations.exists("bloom_filter", "2000"));
        log.info("{}是否存在：{}", 20005, bloomOperations.exists("bloom_filter", "20005"));
        log.info("{}是否存在：{}", 20005 + "" + 2001, bloomOperations.existsMulti("bloom_filter", "20005", "2001"));
    }
}
