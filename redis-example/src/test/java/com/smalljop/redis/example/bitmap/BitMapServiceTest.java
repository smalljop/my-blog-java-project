package com.smalljop.redis.example.bitmap;

import com.smalljop.redis.example.queue.MessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

@Slf4j
@SpringBootTest
class BitMapServiceTest {


    @Autowired
    private BitMapService bitMapService;

    //因为下标是从0开始的 所以我们需要约定一个0
    // 或者你也可以不从0开始 直接以天数20200104这种作为下标 那之前的下标会用0补齐
    // 同时会带来字节的浪费 但是使用方便
    // 是从哪一天开始 比如从从开发的那天 我已 2020 01-01号为例
    LocalDate localDate = LocalDate.of(2020, 01, 01);

    // 算距离1970-01-01有多少天 以这天为起点 之后所有时间减去起点 如   18262 就是我的起点
    Long epochDay = localDate.toEpochDay();


    //我们为每个人维护一个bitmap 作为key
    String key = "user:1:riding:days";

    /**
     * 初始化一批数据
     */
    @Test
    void init() {
        //模拟从今天开始的之后一百天
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Long index = localDate.plusDays(i).toEpochDay() - epochDay;
            //随机模拟 true和false
            boolean aBoolean = random.nextBoolean();
            bitMapService.setBit("user:1:riding:days", index, aBoolean);
            log.info("初始化第{}条数据", i);
        }
    }


    @Test
    void setBit() {
        bitMapService.setBit("testBitmap", 0L, true);
    }

    @Test
    void getBit() {
        bitMapService.getBit("testBitmap", 0L);
    }

    @Test
    void test1() {
        //保存今天已经骑行状态
        long today = LocalDate.now().toEpochDay() - epochDay;
        //查询是否骑行
        Boolean bit = bitMapService.getBit(key, today);
        log.info("今天是否骑行：{}", bit);
        //保存骑行状态
        bitMapService.setBit(key, today, true);
        bit = bitMapService.getBit(key, today);
        log.info("今天是否骑行：{}", bit);
    }

    @Test
    void test2() {
        LocalDate now = LocalDate.now();
        long today = now.toEpochDay() - epochDay;
        //   1. 获取用户当天是否骑行
        Boolean bit = bitMapService.getBit(key, today);
        log.info("今天是否骑行：{}", bit);
        //获取当月天数 开始天数和结束天数
        LocalDate startTime = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endTime = now.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println((startTime.toEpochDay() - epochDay) + "----" + (endTime.toEpochDay() - epochDay));
        // 3.用户第一次骑行时间
        Long day = bitMapService.bitPos(key, true);
        //减去时间差
        LocalDate localDate = now.plusDays(today - day);
        log.info("第一次骑行时间：{}", localDate);
        // 4. 获取用户累计骑行天数
        Long count = bitMapService.bitCount(key);
        log.info("累计骑行天数：{}", count);
    }

}
