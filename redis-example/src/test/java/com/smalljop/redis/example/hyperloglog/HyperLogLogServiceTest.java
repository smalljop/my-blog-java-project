package com.smalljop.redis.example.hyperloglog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class HyperLogLogServiceTest {

    @Autowired
    HyperLogLogService hyperLogLogService;

    @Test
    void test() {
        String key = "HyperLogLog";
        //处理化数组 模拟用户
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        // 循环一百次 模拟一百次访问
        for (int i = 0; i < 1000; i++) {
            int anInt = ThreadLocalRandom.current().nextInt(5);
            hyperLogLogService.add(key, arr[anInt]);
        }
        //统计数量
        Long count = hyperLogLogService.count(key);
        log.info("统计数：{}", count);
    }

}